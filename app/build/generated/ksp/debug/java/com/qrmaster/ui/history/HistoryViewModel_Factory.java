package com.qrmaster.ui.history;

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
public final class HistoryViewModel_Factory implements Factory<HistoryViewModel> {
  private final Provider<QRCodeRepository> repositoryProvider;

  public HistoryViewModel_Factory(Provider<QRCodeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public HistoryViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static HistoryViewModel_Factory create(Provider<QRCodeRepository> repositoryProvider) {
    return new HistoryViewModel_Factory(repositoryProvider);
  }

  public static HistoryViewModel newInstance(QRCodeRepository repository) {
    return new HistoryViewModel(repository);
  }
}
