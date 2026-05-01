package com.qrmaster.ui.generator

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.qrmaster.R
import com.qrmaster.databinding.FragmentGeneratorBinding
import com.qrmaster.domain.model.QRCodeType
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class GeneratorFragment : Fragment() {

    private var _binding: FragmentGeneratorBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GeneratorViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGeneratorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        observeViewModel()
    }

    private fun setupUI() {
        binding.chipGroupType.setOnCheckedStateChangeListener { _, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val type = when (checkedIds[0]) {
                    R.id.chip_url -> QRCodeType.URL
                    R.id.chip_text -> QRCodeType.TEXT
                    R.id.chip_wifi -> QRCodeType.WIFI
                    R.id.chip_vcard -> QRCodeType.VCARD
                    else -> QRCodeType.TEXT
                }
                viewModel.setSelectedType(type)
            }
        }

        binding.btnGenerate.setOnClickListener {
            val content = binding.etContent.text?.toString() ?: ""
            if (content.isNotBlank()) {
                viewModel.generateQRCode(content)
            } else {
                Toast.makeText(requireContext(), R.string.no_content, Toast.LENGTH_SHORT).show()
            }
        }

        binding.btnSave.setOnClickListener {
            val content = binding.etContent.text?.toString() ?: ""
            if (content.isNotBlank()) {
                viewModel.saveQRCode(content)
            }
        }
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.qrBitmap.collect { bitmap ->
                        if (bitmap != null) {
                            binding.ivQrCode.setImageBitmap(bitmap)
                            binding.cardQr.visibility = View.VISIBLE
                        } else {
                            binding.cardQr.visibility = View.GONE
                        }
                    }
                }

                launch {
                    viewModel.saveState.collect { state ->
                        when (state) {
                            is SaveState.Saved -> {
                                Toast.makeText(requireContext(), R.string.qr_saved, Toast.LENGTH_SHORT).show()
                                viewModel.resetSaveState()
                            }
                            is SaveState.Error -> {
                                Toast.makeText(requireContext(), state.message, Toast.LENGTH_SHORT).show()
                                viewModel.resetSaveState()
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}