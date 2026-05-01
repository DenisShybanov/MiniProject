package com.qrmaster.di;

import android.content.Context;
import com.qrmaster.data.local.QRCodeDatabase;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
@QualifierMetadata("dagger.hilt.android.qualifiers.ApplicationContext")
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
public final class AppModule_ProvideQRCodeDatabaseFactory implements Factory<QRCodeDatabase> {
  private final Provider<Context> contextProvider;

  public AppModule_ProvideQRCodeDatabaseFactory(Provider<Context> contextProvider) {
    this.contextProvider = contextProvider;
  }

  @Override
  public QRCodeDatabase get() {
    return provideQRCodeDatabase(contextProvider.get());
  }

  public static AppModule_ProvideQRCodeDatabaseFactory create(Provider<Context> contextProvider) {
    return new AppModule_ProvideQRCodeDatabaseFactory(contextProvider);
  }

  public static QRCodeDatabase provideQRCodeDatabase(Context context) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideQRCodeDatabase(context));
  }
}
