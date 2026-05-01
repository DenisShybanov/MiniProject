package com.qrmaster.data.repository;

import com.qrmaster.data.local.QRCodeDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class QRCodeRepositoryImpl_Factory implements Factory<QRCodeRepositoryImpl> {
  private final Provider<QRCodeDao> qrCodeDaoProvider;

  public QRCodeRepositoryImpl_Factory(Provider<QRCodeDao> qrCodeDaoProvider) {
    this.qrCodeDaoProvider = qrCodeDaoProvider;
  }

  @Override
  public QRCodeRepositoryImpl get() {
    return newInstance(qrCodeDaoProvider.get());
  }

  public static QRCodeRepositoryImpl_Factory create(Provider<QRCodeDao> qrCodeDaoProvider) {
    return new QRCodeRepositoryImpl_Factory(qrCodeDaoProvider);
  }

  public static QRCodeRepositoryImpl newInstance(QRCodeDao qrCodeDao) {
    return new QRCodeRepositoryImpl(qrCodeDao);
  }
}
