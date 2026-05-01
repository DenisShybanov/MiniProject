package com.qrmaster.ui.generator

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.domain.repository.QRCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class GeneratorViewModel @Inject constructor(
    private val repository: QRCodeRepository
) : ViewModel() {

    private val _qrBitmap = MutableStateFlow<Bitmap?>(null)
    val qrBitmap: StateFlow<Bitmap?> = _qrBitmap.asStateFlow()

    private val _selectedType = MutableStateFlow(QRCodeType.URL)
    val selectedType: StateFlow<QRCodeType> = _selectedType.asStateFlow()

    private val _saveState = MutableStateFlow<SaveState>(SaveState.Idle)
    val saveState: StateFlow<SaveState> = _saveState.asStateFlow()

    fun setSelectedType(type: QRCodeType) {
        _selectedType.value = type
    }

    fun generateQRCode(content: String) {
        if (content.isBlank()) {
            _saveState.value = SaveState.Error("Введіть вміст")
            return
        }

        viewModelScope.launch {
            try {
                val bitmap = withContext(Dispatchers.Default) {
                    generateQRBitmap(content)
                }
                _qrBitmap.value = bitmap
                _saveState.value = SaveState.Success
            } catch (e: Exception) {
                _saveState.value = SaveState.Error(e.message ?: "Помилка генерації")
            }
        }
    }

    private fun generateQRBitmap(content: String): Bitmap {
        val size = 512
        val hints = hashMapOf<EncodeHintType, Any>()
        hints[EncodeHintType.CHARACTER_SET] = "UTF-8"
        hints[EncodeHintType.MARGIN] = 1

        val writer = QRCodeWriter()
        val bitMatrix = writer.encode(content, BarcodeFormat.QR_CODE, size, size, hints)

        val bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888)
        for (x in 0 until size) {
            for (y in 0 until size) {
                bitmap.setPixel(x, y, if (bitMatrix[x, y]) android.graphics.Color.BLACK else android.graphics.Color.WHITE)
            }
        }
        return bitmap
    }

    fun saveQRCode(content: String, title: String = "") {
        viewModelScope.launch {
            try {
                val qrCode = QRCode(
                    content = content,
                    type = _selectedType.value,
                    timestamp = System.currentTimeMillis(),
                    isGenerated = true,
                    title = title
                )
                repository.saveQRCode(qrCode)
                _saveState.value = SaveState.Saved
            } catch (e: Exception) {
                _saveState.value = SaveState.Error(e.message ?: "Помилка збереження")
            }
        }
    }

    fun resetSaveState() {
        _saveState.value = SaveState.Idle
    }
}

sealed class SaveState {
    object Idle : SaveState()
    object Success : SaveState()
    object Saved : SaveState()
    data class Error(val message: String) : SaveState()
}
