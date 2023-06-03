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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.capstone.tesfirebase.data.repository.News
import com.capstone.tesfirebase.data.repository.NewsData
import com.capstone.tesfirebase.ui.fruit.information.FruitInformationActivity
import com.capstone.tesfirebase.ui.fruit.storage.FruitStorageActivity
import com.capstone.tesfirebase.ui.fruit.treecare.FruitTreeCareActivity
import com.capstone.tesfirebase.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    private lateinit var listNews: ArrayList<News>

    private val fruits = arrayOf("Apel")
    private lateinit var selectedFruit: String

    private val onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedFruit = parent?.getItemAtPosition(position).toString()
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

        // Set up news adapter for rvNews
        val rvNews = binding.rvNews
        rvNews.layoutManager = LinearLayoutManager(requireActivity(), RecyclerView.HORIZONTAL, false)
        rvNews.setHasFixedSize(true)
        listNews = ArrayList()
        listNews.addAll(NewsData.newsList)

        val newsAdapter = NewsAdapter(listNews)
        rvNews.adapter = newsAdapter

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