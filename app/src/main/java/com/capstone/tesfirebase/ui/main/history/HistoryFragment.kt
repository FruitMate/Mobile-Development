package com.capstone.tesfirebase.ui.main.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tesfirebase.data.repository.HistoryItem
import com.capstone.tesfirebase.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    private val historyList = ArrayList<HistoryItem>()

    @SuppressLint("NotifyDataSetChanged")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // Create the adapter
        historyAdapter = HistoryAdapter(historyList)

        // Set up RecyclerView
        binding.rvStorymain.layoutManager = LinearLayoutManager(requireContext())
        binding.rvStorymain.adapter = historyAdapter


//        // Add example data to the history list
//        historyList.addAll()

        // Notify the adapter that the data has changed
        historyAdapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}