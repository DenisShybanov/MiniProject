package com.qrmaster.ui.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.qrmaster.databinding.ItemQrCodeBinding
import com.qrmaster.domain.model.QRCode
import java.text.SimpleDateFormat
import java.util.*

class QRCodeAdapter(
    private val onItemClick: (QRCode) -> Unit
) : ListAdapter<QRCode, QRCodeAdapter.QRCodeViewHolder>(QRCodeDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QRCodeViewHolder {
        val binding = ItemQrCodeBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return QRCodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QRCodeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class QRCodeViewHolder(
        private val binding: ItemQrCodeBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        private val dateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())

        fun bind(qrCode: QRCode) {
            binding.tvTitle.text = qrCode.title.ifEmpty { qrCode.type.name }
            binding.tvContent.text = qrCode.content
            binding.tvType.text = qrCode.type.name
            binding.tvTimestamp.text = dateFormat.format(Date(qrCode.timestamp))

            binding.root.setOnClickListener {
                onItemClick(qrCode)
            }
        }
    }

    class QRCodeDiffCallback : DiffUtil.ItemCallback<QRCode>() {
        override fun areItemsTheSame(oldItem: QRCode, newItem: QRCode): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: QRCode, newItem: QRCode): Boolean {
            return oldItem == newItem
        }
    }
}