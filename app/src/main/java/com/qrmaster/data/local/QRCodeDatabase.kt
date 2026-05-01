package com.qrmaster.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [QRCodeEntity::class],
    version = 1,
    exportSchema = false
)
abstract class QRCodeDatabase : RoomDatabase() {
    abstract fun qrCodeDao(): QRCodeDao
}