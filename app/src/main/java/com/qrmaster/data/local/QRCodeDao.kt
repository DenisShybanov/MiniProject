package com.qrmaster.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface QRCodeDao {
    @Query("SELECT * FROM qr_codes ORDER BY timestamp DESC")
    fun getAllQRCodes(): Flow<List<QRCodeEntity>>

    @Query("SELECT * FROM qr_codes WHERE type = :type ORDER BY timestamp DESC")
    fun getQRCodesByType(type: String): Flow<List<QRCodeEntity>>

    @Query("SELECT * FROM qr_codes WHERE title LIKE '%' || :query || '%' OR content LIKE '%' || :query || '%' ORDER BY timestamp DESC")
    fun searchQRCodes(query: String): Flow<List<QRCodeEntity>>

    @Query("SELECT * FROM qr_codes WHERE id = :id")
    suspend fun getQRCodeById(id: Long): QRCodeEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertQRCode(qrCode: QRCodeEntity): Long

    @Update
    suspend fun updateQRCode(qrCode: QRCodeEntity)

    @Delete
    suspend fun deleteQRCode(qrCode: QRCodeEntity)

    @Query("DELETE FROM qr_codes WHERE id = :id")
    suspend fun deleteQRCodeById(id: Long)
}