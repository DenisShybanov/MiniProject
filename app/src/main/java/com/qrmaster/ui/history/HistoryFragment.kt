package com.qrmaster.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.qrmaster.R
import com.qrmaster.databinding.FragmentHistoryBinding
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.ui.details.DetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by viewModels()
    private lateinit var adapter: QRCodeAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearch()
        setupFilters()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        adapter = QRCodeAdapter { qrCode ->
            openDetails(qrCode)
        }

        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@HistoryFragment.adapter
        }

        // Swipe to delete
        val swipeHandler = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean = false

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val qrCode = adapter.currentList[position]
                viewModel.deleteQRCode(qrCode.id)

                Snackbar.make(binding.root, R.string.qr_deleted, Snackbar.LENGTH_LONG)
                    .setAction(R.string.undo) {
                        // Undo would require keeping the deleted item temporarily
                    }
                    .show()
            }
        }

        ItemTouchHelper(swipeHandler).attachToRecyclerView(binding.rvHistory)
    }

    private fun setupSearch() {
        binding.etSearch.addTextChangedListener { text ->
            viewModel.setSearchQuery(text?.toString() ?: "")
        }
    }

    private fun setupFilters() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val filter = when (checkedIds[0]) {
                    R.id.chip_url -> QRCodeType.URL
                    R.id.chip_text -> QRCodeType.TEXT
                    R.id.chip_wifi -> QRCodeType.WIFI
                    R.id.chip_vcard -> QRCodeType.VCARD
                    else -> null
                }
                viewModel.setFilter(filter)
            } else {
                viewModel.setFilter(null)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.qrCodes.collect { codes ->
                    adapter.submitList(codes)
                    binding.tvEmpty.visibility = if (codes.isEmpty()) View.VISIBLE else View.GONE
                }
            }
        }
    }

    private fun openDetails(qrCode: QRCode) {
        val intent = Intent(requireContext(), DetailsActivity::class.java).apply {
            putExtra(DetailsActivity.EXTRA_QR_CODE, qrCode)
        }
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}