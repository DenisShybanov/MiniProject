package com.qrmaster.ui.generator;

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
public final class GeneratorViewModel_Factory implements Factory<GeneratorViewModel> {
  private final Provider<QRCodeRepository> repositoryProvider;

  public GeneratorViewModel_Factory(Provider<QRCodeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public GeneratorViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static GeneratorViewModel_Factory create(Provider<QRCodeRepository> repositoryProvider) {
    return new GeneratorViewModel_Factory(repositoryProvider);
  }

  public static GeneratorViewModel newInstance(QRCodeRepository repository) {
    return new GeneratorViewModel(repository);
  }
}
