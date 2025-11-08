package com.example.btth

import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

// State riêng cho Phone Auth (giúp UI biết khi nào cần nhập OTP)
sealed class PhoneAuthState {
    object Idle : PhoneAuthState()
    object CodeSent : PhoneAuthState()
    data class Error(val message: String) : PhoneAuthState()
}

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // StateFlow để theo dõi trạng thái người dùng (đăng nhập hay chưa)
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    // StateFlow để thông báo kết quả đăng nhập/đăng xuất
    private val _signInStatus = MutableStateFlow<String?>(null)
    val signInStatus: StateFlow<String?> = _signInStatus

    // StateFlow dành riêng cho luồng Đăng nhập bằng Số điện thoại
    private val _phoneAuthState = MutableStateFlow<PhoneAuthState>(PhoneAuthState.Idle)
    val phoneAuthState: StateFlow<PhoneAuthState> = _phoneAuthState

    // Biến tạm để lưu ID xác thực khi mã OTP được gửi đi
    var verificationId: String? = null

    // +++ HÀM ĐƯỢC THÊM VÀO +++
    /**
     * Cập nhật thông báo trạng thái đăng nhập để hiển thị ra UI.
     * Được gọi từ LoginScreen khi có lỗi xảy ra ở phía client (ví dụ: hủy đăng nhập Google).
     */
    fun updateSignInStatus(status: String) {
        _signInStatus.value = status
    }

    // --- LOGIC PHONE AUTH CALLBACKS ---
    val phoneAuthCallbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            // Tự động xác minh: Firebase tự động xác thực mã OTP
            signInWithCredential(credential, isPhoneAuth = true)
        }

        override fun onVerificationFailed(e: FirebaseException) {
            // Xảy ra lỗi (số điện thoại không hợp lệ, bị giới hạn,...)
            _phoneAuthState.value = PhoneAuthState.Error(e.message ?: "Lỗi xác thực số điện thoại")
            _signInStatus.value = "Phone Auth Failed: ${e.message}"
        }

        override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
            // Gửi mã thành công, lưu ID để dùng xác thực sau
            verificationId = id
            _phoneAuthState.value = PhoneAuthState.CodeSent // Thông báo cho UI hiển thị ô nhập OTP
            _signInStatus.value = "Verification code sent to your phone."
        }
    }

    // --- HÀM CHUNG CHO ĐĂNG NHẬP (Google & Phone) ---
    private fun signInWithCredential(credential: AuthCredential, isPhoneAuth: Boolean = false) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    val authType = if (isPhoneAuth) "Phone" else "Google"
                    _signInStatus.value = "Success! Logged in via $authType.\nWelcome to UTHStudentTasks"
                } else {
                    _signInStatus.value = "Error: Sign-In Failed\n${task.exception?.message}"
                }
            }
    }

    // --- HÀM ĐĂNG NHẬP GOOGLE ---
    fun signInWithGoogleCredential(credential: AuthCredential) {
        signInWithCredential(credential, isPhoneAuth = false)
    }

    fun getGoogleAuthCredential(idToken: String): AuthCredential {
        return GoogleAuthProvider.getCredential(idToken, null)
    }

    // --- HÀM XÁC THỰC PHONE OTP ---
    fun verifyCodeAndSignIn(code: String) {
        val id = verificationId
        if (id != null) {
            val credential = PhoneAuthProvider.getCredential(id, code)
            signInWithCredential(credential, isPhoneAuth = true)
        } else {
            _signInStatus.value = "Error: Verification ID missing. Please resend the code."
        }
    }

    fun resetPhoneAuthState() {
        _phoneAuthState.value = PhoneAuthState.Idle
    }

    // --- HÀM ĐĂNG XUẤT ---
    fun signOut() {
        auth.signOut()
        _user.value = null
        _signInStatus.value = "Signed out successfully."
        _phoneAuthState.value = PhoneAuthState.Idle // Reset trạng thái Phone Auth
        verificationId = null
    }
}
