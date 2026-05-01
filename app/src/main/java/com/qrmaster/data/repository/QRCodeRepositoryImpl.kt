package com.qrmaster.data.repository

import com.qrmaster.data.local.QRCodeDao
import com.qrmaster.data.local.QRCodeEntity
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.domain.repository.QRCodeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QRCodeRepositoryImpl @Inject constructor(
    private val qrCodeDao: QRCodeDao
) : QRCodeRepository {

    override fun getAllQRCodes(): Flow<List<QRCode>> {
        return qrCodeDao.getAllQRCodes().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getQRCodesByType(type: QRCodeType): Flow<List<QRCode>> {
        return qrCodeDao.getQRCodesByType(type.name).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun searchQRCodes(query: String): Flow<List<QRCode>> {
        return qrCodeDao.searchQRCodes(query).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun getQRCodeById(id: Long): QRCode? {
        return qrCodeDao.getQRCodeById(id)?.toDomain()
    }

    override suspend fun saveQRCode(qrCode: QRCode): Long {
        return qrCodeDao.insertQRCode(qrCode.toEntity())
    }

    override suspend fun updateQRCode(qrCode: QRCode) {
        qrCodeDao.updateQRCode(qrCode.toEntity())
    }

    override suspend fun deleteQRCode(id: Long) {
        qrCodeDao.deleteQRCodeById(id)
    }

    private fun QRCodeEntity.toDomain(): QRCode {
        return QRCode(
            id = id,
            content = content,
            type = QRCodeType.fromString(type),
            timestamp = timestamp,
            isGenerated = isGenerated,
            title = title
        )
    }

    private fun QRCode.toEntity(): QRCodeEntity {
        return QRCodeEntity(
            id = id,
            content = content,
            type = type.name,
            timestamp = timestamp,
            isGenerated = isGenerated,
            title = title
        )
    }
}