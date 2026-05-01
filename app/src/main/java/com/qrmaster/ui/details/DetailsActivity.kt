package com.qrmaster.ui.details

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.qrcode.QRCodeWriter
import com.qrmaster.R
import com.qrmaster.databinding.ActivityDetailsBinding
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.domain.repository.QRCodeRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding
    private var qrCode: QRCode? = null

    @Inject
    lateinit var repository: QRCodeRepository

    companion object {
        const val EXTRA_QR_CODE = "extra_qr_code"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        qrCode = intent.getParcelableExtra(EXTRA_QR_CODE)

        if (qrCode == null) {
            finish()
            return
        }

        setupUI()
        displayQRCode()
    }

    private fun setupUI() {
        binding.etTitle.setText(qrCode?.title ?: "")
        binding.tvContent.text = qrCode?.content ?: ""

        binding.btnShare.setOnClickListener {
            shareContent()
        }

        binding.btnOpen.setOnClickListener {
            openContent()
        }

        binding.btnCopy.setOnClickListener {
            copyContent()
        }
    }

    private fun displayQRCode() {
        val content = qrCode?.content ?: return

        lifecycleScope.launch {
            val bitmap = withContext(Dispatchers.Default) {
                generateQRBitmap(content)
            }
            binding.ivQrCode.setImageBitmap(bitmap)
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

    private fun shareContent() {
        val content = qrCode?.content ?: return
        val shareIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, content)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(shareIntent, getString(R.string.btn_share)))
    }

    private fun openContent() {
        val content = qrCode?.content ?: return

        if (qrCode?.type == QRCodeType.URL) {
            try {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(content))
                startActivity(intent)
            } catch (e: Exception) {
                Toast.makeText(this, R.string.open_failed, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(this, R.string.open_url_only, Toast.LENGTH_SHORT).show()
        }
    }

    private fun copyContent() {
        val content = qrCode?.content ?: return
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("QR Code Content", content)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, R.string.copied_to_clipboard, Toast.LENGTH_SHORT).show()
    }

    override fun onPause() {
        super.onPause()
        val current = qrCode ?: return
        val newTitle = binding.etTitle.text?.toString()?.trim().orEmpty()
        if (current.id != 0L && current.title != newTitle) {
            qrCode = current.copy(title = newTitle)
            lifecycleScope.launch {
                repository.updateQRCode(qrCode!!)
            }
        }
    }
}
