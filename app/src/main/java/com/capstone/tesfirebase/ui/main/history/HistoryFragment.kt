package com.capstone.tesfirebase.ui.main.history

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.tesfirebase.data.response.HistoryResponse
import com.capstone.tesfirebase.databinding.FragmentHistoryBinding

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private lateinit var historyAdapter: HistoryAdapter
    private val historyList = ArrayList<HistoryResponse>()

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

        // Example data
        val exampleHistory = listOf(
            HistoryResponse("iYqXpePAHAd97Rwum1lNMPpTk111", "overripe", "2023-06-07 15:43:59", "https://cdn-2.tstatic.net/pontianak/foto/bank/images/jelaskan-proses-pematangan-apel-tempat-tumbuh-apel-dan-tekstur-kulit-apel.jpg"),
            HistoryResponse("iYqXpePAHAd97Rwum1lNMPpTk111", "unripe", "2023-06-07 15:56:07", "https://cdn-2.tstatic.net/pontianak/foto/bank/images/jelaskan-proses-pematangan-apel-tempat-tumbuh-apel-dan-tekstur-kulit-apel.jpg"),
            HistoryResponse("iYqXpePAHAd97Rwum1lNMPpTk111", "ripe", "2023-06-07 15:22:19", "https://cdn-2.tstatic.net/pontianak/foto/bank/images/jelaskan-proses-pematangan-apel-tempat-tumbuh-apel-dan-tekstur-kulit-apel.jpg"),
            HistoryResponse("iYqXpePAHAd97Rwum1lNMPpTk111", "ripe", "2023-06-07 15:40:43", "https://cdn-2.tstatic.net/pontianak/foto/bank/images/jelaskan-proses-pematangan-apel-tempat-tumbuh-apel-dan-tekstur-kulit-apel.jpg")
        )
//        // Add example data to the history list
        historyList.addAll(exampleHistory)

        // Notify the adapter that the data has changed
        historyAdapter.notifyDataSetChanged()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}