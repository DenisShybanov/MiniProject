package com.qrmaster.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class QRCode(
    val id: Long = 0,
    val content: String,
    val type: QRCodeType,
    val timestamp: Long,
    val isGenerated: Boolean,
    val title: String = ""
) : Parcelable

enum class QRCodeType {
    URL,
    TEXT,
    WIFI,
    VCARD;

    companion object {
        fun fromString(value: String): QRCodeType {
            return when (value.uppercase()) {
                "URL" -> URL
                "TEXT" -> TEXT
                "WIFI" -> WIFI
                "VCARD" -> VCARD
                else -> TEXT
            }
        }
    }
}