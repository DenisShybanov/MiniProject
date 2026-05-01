package com.qrmaster.di

import android.content.Context
import androidx.room.Room
import com.qrmaster.data.local.QRCodeDao
import com.qrmaster.data.local.QRCodeDatabase
import com.qrmaster.data.repository.QRCodeRepositoryImpl
import com.qrmaster.domain.repository.QRCodeRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideQRCodeDatabase(
        @ApplicationContext context: Context
    ): QRCodeDatabase {
        return Room.databaseBuilder(
            context,
            QRCodeDatabase::class.java,
            "qr_master_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideQRCodeDao(database: QRCodeDatabase): QRCodeDao {
        return database.qrCodeDao()
    }

    @Provides
    @Singleton
    fun provideQRCodeRepository(
        qrCodeDao: QRCodeDao
    ): QRCodeRepository {
        return QRCodeRepositoryImpl(qrCodeDao)
    }
}