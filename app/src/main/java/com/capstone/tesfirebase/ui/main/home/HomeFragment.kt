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
import com.capstone.tesfirebase.FruitInformationActivity
import com.capstone.tesfirebase.FruitStorageActivity
import com.capstone.tesfirebase.FruitTreeCareActivity
import com.capstone.tesfirebase.databinding.FragmentHomeBinding
import com.capstone.tesfirebase.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

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

        // Spinner untuk memilih buah
        val adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, fruits)
        adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.fruitSpinner.adapter = adapter
        binding.fruitSpinner.onItemSelectedListener = onItemSelectedListener

        // Button-button pada horizontalScrollView
        binding.apply {
            fruitInformation.setOnClickListener {
                val intent = Intent(requireContext(), FruitInformationActivity::class.java)
                startActivity(intent)
            }
            fruitStorage.setOnClickListener {
                val intent = Intent(requireContext(), FruitStorageActivity::class.java)
                startActivity(intent)
            }
            fruitTreeCare.setOnClickListener {
                val intent = Intent(requireContext(), FruitTreeCareActivity::class.java)
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