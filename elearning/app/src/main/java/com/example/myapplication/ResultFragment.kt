package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController

class ResultFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val resultMessage = arguments?.getString("result") ?: "You Won!"

        return ComposeView(requireContext()).apply {
            // `apply` bây giờ được gọi = đối tượng ComposeView
            setContent {
                ResultScreen(
                    result = resultMessage,
                    onPlayAgain = {
                        // Sử dụng findNavController trực tiếp, không cần view
                        findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
                    }
                )
            }
        }
    }
}
