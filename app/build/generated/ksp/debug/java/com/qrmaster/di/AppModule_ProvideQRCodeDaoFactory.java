package com.qrmaster.di;

import com.qrmaster.data.local.QRCodeDao;
import com.qrmaster.data.local.QRCodeDatabase;
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
public final class AppModule_ProvideQRCodeDaoFactory implements Factory<QRCodeDao> {
  private final Provider<QRCodeDatabase> databaseProvider;

  public AppModule_ProvideQRCodeDaoFactory(Provider<QRCodeDatabase> databaseProvider) {
    this.databaseProvider = databaseProvider;
  }

  @Override
  public QRCodeDao get() {
    return provideQRCodeDao(databaseProvider.get());
  }

  public static AppModule_ProvideQRCodeDaoFactory create(
      Provider<QRCodeDatabase> databaseProvider) {
    return new AppModule_ProvideQRCodeDaoFactory(databaseProvider);
  }

  public static QRCodeDao provideQRCodeDao(QRCodeDatabase database) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQRCodeDao(database));
  }
}
