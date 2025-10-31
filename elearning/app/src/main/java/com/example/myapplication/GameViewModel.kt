package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {
    private val words = listOf("ANDROID", "DEVELOPER", "KOTLIN", "STUDIO")
    private  var secretWord: String = "T"

    private val _secretWordDisplay = MutableLiveData<String>()
    val secretWordDisplay: LiveData<String> get() = _secretWordDisplay

    private val _incorrectGuesses = MutableLiveData("")
    val incorrectGuesses: LiveData<String> get() = _incorrectGuesses

    private val _livesLeft = MutableLiveData(8)
    val livesLeft: LiveData<Int> get() = _livesLeft

    private val _gameOver = MutableLiveData(false)
    val gameOver: LiveData<Boolean> get() = _gameOver

    private var lettersGuessed = ""

    init {
        startNewGame()
    }

    fun startNewGame() {
        lettersGuessed = ""
        _gameOver.postValue(false)
        _livesLeft.postValue(8)
        _incorrectGuesses.postValue("")
        secretWord = words.random().uppercase()
        updateSecretWordDisplay()
    }

    fun makeGuess(guess: String) {
        if (guess.length == 1 && _gameOver.value == false) {
            val letter = guess.uppercase()

            if (!lettersGuessed.contains(letter)) {
                lettersGuessed += letter

                if (secretWord.contains(letter)) {
                    // Hàm này giờ sẽ tự kiểm tra điều kiện thắng
                    updateSecretWordDisplay()
                } else {
                    // Logic xử lý đoán sai được đưa vào hàm riêng
                    handleIncorrectGuess(letter)
                }
            }
        }
    }

    private fun updateSecretWordDisplay() {
        var display = ""
        secretWord.forEach {
            display += if (lettersGuessed.contains(it.toString())) {
                it.toString()
            } else {
                "_"
            }
        }
        _secretWordDisplay.postValue(display)

        // Kiểm tra thắng NGAY LẬP TỨC sau khi có chuỗi mới
        if (!display.contains('_')) {
            _gameOver.postValue(true)
        }
    }

    // Tách riêng logic xử lý đoán sai để code rõ ràng hơn
    private fun handleIncorrectGuess(letter: String) {
        _incorrectGuesses.postValue((_incorrectGuesses.value ?: "") + "$letter ")
        val newLives = (_livesLeft.value ?: 0) - 1
        _livesLeft.postValue(newLives)

        // Kiểm tra thua NGAY LẬP TỨC sau khi trừ mạng
        if (newLives <= 0) {
            _gameOver.postValue(true)
        }
    }

    fun wonLostMessage(): String {
        return if (_secretWordDisplay.value?.contains('_') == false) {
            "You won! The word was $secretWord."
        } else {
            "You lost! The word was $secretWord."
        }
    }
}
