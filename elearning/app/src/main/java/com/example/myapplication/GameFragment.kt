package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController

class GameFragment : Fragment() {

    private val viewModel: GameViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {

        viewModel.gameOver.observe(viewLifecycleOwner) { isGameOver ->
            if (isGameOver) {
                val message = viewModel.wonLostMessage()
                val bundle = bundleOf("result" to message)
                view?.findNavController()?.navigate(R.id.action_gameFragment_to_resultFragment, bundle)
            }
        }

        // --- PHẦN COMPOSE ---
        return ComposeView(requireContext()).apply {
            setContent {
                // Gọi đến GameScreen để vẽ giao diện
                GameScreen(viewModel = viewModel)
            }
        }
    }

}
