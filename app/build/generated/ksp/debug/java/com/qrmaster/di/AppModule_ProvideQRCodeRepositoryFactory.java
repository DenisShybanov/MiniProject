package com.qrmaster.di;

import com.qrmaster.data.local.QRCodeDao;
import com.qrmaster.domain.repository.QRCodeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
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
public final class AppModule_ProvideQRCodeRepositoryFactory implements Factory<QRCodeRepository> {
  private final Provider<QRCodeDao> qrCodeDaoProvider;

  public AppModule_ProvideQRCodeRepositoryFactory(Provider<QRCodeDao> qrCodeDaoProvider) {
    this.qrCodeDaoProvider = qrCodeDaoProvider;
  }

  @Override
  public QRCodeRepository get() {
    return provideQRCodeRepository(qrCodeDaoProvider.get());
  }

  public static AppModule_ProvideQRCodeRepositoryFactory create(
      Provider<QRCodeDao> qrCodeDaoProvider) {
    return new AppModule_ProvideQRCodeRepositoryFactory(qrCodeDaoProvider);
  }

  public static QRCodeRepository provideQRCodeRepository(QRCodeDao qrCodeDao) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQRCodeRepository(qrCodeDao));
  }
}
