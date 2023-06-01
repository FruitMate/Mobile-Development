package com.capstone.tesfirebase.ui.main.home

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.tesfirebase.PenyimpananBuahActivity
import com.capstone.tesfirebase.PerawatanTanamanActivity
import com.capstone.tesfirebase.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private lateinit var auth: FirebaseAuth

    private val fruits = arrayOf("Apel")
    private lateinit var selectedFruit: String

    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val selectedItem = parent?.getItemAtPosition(position).toString()
        }

        override fun onNothingSelected(parent: AdapterView<*>?) {
            // Handle case when nothing is selected
        }
    }

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        /*val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, fruits)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.fruitSpinner.adapter = adapter
        binding.fruitSpinner.onItemSelectedListener = onItemSelectedListener

        binding.apply {
            fruitInformation.setOnClickListener {
                // Pindah ke halaman informasi buah
            }
            fruitStorage.setOnClickListener {
                val intent = Intent(requireContext(), PenyimpananBuahActivity::class.java)
                startActivity(intent)
            }
            fruitTreeCare.setOnClickListener {
                val intent = Intent(requireContext(), PerawatanTanamanActivity::class.java)
                startActivity(intent)
            }
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}