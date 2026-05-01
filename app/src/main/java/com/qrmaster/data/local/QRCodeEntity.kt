package com.qrmaster.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "qr_codes")
data class QRCodeEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val content: String,
    val type: String,
    val timestamp: Long,
    val isGenerated: Boolean,
    val title: String = ""
)