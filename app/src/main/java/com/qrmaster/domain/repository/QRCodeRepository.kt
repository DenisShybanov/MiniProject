package com.qrmaster.domain.repository

import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import kotlinx.coroutines.flow.Flow

interface QRCodeRepository {
    fun getAllQRCodes(): Flow<List<QRCode>>
    fun getQRCodesByType(type: QRCodeType): Flow<List<QRCode>>
    fun searchQRCodes(query: String): Flow<List<QRCode>>
    suspend fun getQRCodeById(id: Long): QRCode?
    suspend fun saveQRCode(qrCode: QRCode): Long
    suspend fun updateQRCode(qrCode: QRCode)
    suspend fun deleteQRCode(id: Long)
}