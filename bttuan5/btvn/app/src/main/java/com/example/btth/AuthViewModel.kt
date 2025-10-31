package com.example.btth

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider // <--- THÊM DÒNG IMPORT NÀY
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class AuthViewModel : ViewModel() {
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()

    // StateFlow để theo dõi trạng thái người dùng (đăng nhập hay chưa)
    private val _user = MutableStateFlow<FirebaseUser?>(auth.currentUser)
    val user: StateFlow<FirebaseUser?> = _user

    // StateFlow để thông báo kết quả đăng nhập/đăng xuất
    private val _signInStatus = MutableStateFlow<String?>(null)
    val signInStatus: StateFlow<String?> = _signInStatus

    /**
     * Thực hiện đăng nhập vào Firebase bằng Google Credential.
     */
    fun signInWithGoogleCredential(credential: AuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _user.value = auth.currentUser
                    _signInStatus.value = "Success!\nID: ${auth.currentUser?.email}\nWelcome to UTHStudentTasks"
                } else {
                    _signInStatus.value = "Error: Google Sign-In Failed\n${task.exception?.message}"
                }
            }
    }

    /**
     * Thực hiện đăng xuất khỏi Firebase.
     */
    fun signOut() {
        auth.signOut()
        _user.value = null // Cập nhật trạng thái người dùng là đã đăng xuất
        _signInStatus.value = "Signed out successfully." // Xóa thông báo cũ
    }

    // --- THÊM HÀM BỊ THIẾU VÀO ĐÂY ---
    /**
     * Tạo AuthCredential từ Google ID Token.
     */
    fun getGoogleAuthCredential(idToken: String): AuthCredential {
        return GoogleAuthProvider.getCredential(idToken, null)
    }
}
