package com.capstone.tesfirebase.ui.main.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.capstone.tesfirebase.R
import com.capstone.tesfirebase.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val dashboardViewModel =
            ViewModelProvider(this).get(HistoryViewModel::class.java)

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHistory
        dashboardViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        // Tampilkan pop-up pengembangan
        showDevelopmentPopup()
        return root
    }
    private fun showDevelopmentPopup() {
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setMessage("Halaman sedang dalam pengembangan")
            .setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        val dialog = dialogBuilder.create()
        dialog.show()
        navigateToHome()
    }

    private fun navigateToHome() {
        val navController = findNavController()
        navController.navigate(R.id.navigation_home)
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}