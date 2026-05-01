package com.qrmaster.ui.scanner;

import com.qrmaster.domain.repository.QRCodeRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
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
public final class ScannerViewModel_Factory implements Factory<ScannerViewModel> {
  private final Provider<QRCodeRepository> repositoryProvider;

  public ScannerViewModel_Factory(Provider<QRCodeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public ScannerViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static ScannerViewModel_Factory create(Provider<QRCodeRepository> repositoryProvider) {
    return new ScannerViewModel_Factory(repositoryProvider);
  }

  public static ScannerViewModel newInstance(QRCodeRepository repository) {
    return new ScannerViewModel(repository);
  }
}
