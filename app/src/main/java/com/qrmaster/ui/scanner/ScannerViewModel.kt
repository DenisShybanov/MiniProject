package com.qrmaster.ui.scanner

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.mlkit.vision.barcode.BarcodeScanner
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.common.InputImage
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.domain.repository.QRCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScannerViewModel @Inject constructor(
    private val repository: QRCodeRepository
) : ViewModel() {

    private val _scanResult = MutableStateFlow<ScanState>(ScanState.Idle)
    val scanResult: StateFlow<ScanState> = _scanResult.asStateFlow()

    private val _isFlashOn = MutableStateFlow(false)
    val isFlashOn: StateFlow<Boolean> = _isFlashOn.asStateFlow()

    val barcodeScanner: BarcodeScanner

    init {
        val options = BarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        barcodeScanner = BarcodeScanning.getClient(options)
    }

    fun processImage(image: InputImage) {
        barcodeScanner.process(image)
            .addOnSuccessListener { barcodes ->
                for (barcode in barcodes) {
                    barcode.rawValue?.let { value ->
                        if (_scanResult.value == ScanState.Idle) {
                            saveScannedCode(value)
                        }
                    }
                }
            }
            .addOnFailureListener { e ->
                Log.e("ScannerViewModel", "Barcode scanning failed", e)
                _scanResult.value = ScanState.Error(e.message ?: "Unknown error")
            }
    }

    fun saveScannedCode(content: String) {
        if (_scanResult.value != ScanState.Idle) return
        _scanResult.value = ScanState.Processing
        viewModelScope.launch {
            try {
                val type = detectQRType(content)
                val qrCode = QRCode(
                    content = content,
                    type = type,
                    timestamp = System.currentTimeMillis(),
                    isGenerated = false,
                    title = ""
                )
                val id = repository.saveQRCode(qrCode)
                _scanResult.value = ScanState.Success(qrCode.copy(id = id))
            } catch (e: Exception) {
                _scanResult.value = ScanState.Error(e.message ?: "Помилка збереження")
            }
        }
    }

    private fun detectQRType(content: String): QRCodeType {
        return when {
            content.startsWith("http://") || content.startsWith("https://") -> QRCodeType.URL
            content.startsWith("WIFI:") -> QRCodeType.WIFI
            content.startsWith("BEGIN:VCARD") -> QRCodeType.VCARD
            else -> QRCodeType.TEXT
        }
    }

    fun toggleFlash() {
        _isFlashOn.value = !_isFlashOn.value
    }

    fun resetScanState() {
        _scanResult.value = ScanState.Idle
    }

    override fun onCleared() {
        super.onCleared()
        barcodeScanner.close()
    }
}

sealed class ScanState {
    object Idle : ScanState()
    object Processing : ScanState()
    data class Success(val qrCode: QRCode) : ScanState()
    data class Error(val message: String) : ScanState()
}
