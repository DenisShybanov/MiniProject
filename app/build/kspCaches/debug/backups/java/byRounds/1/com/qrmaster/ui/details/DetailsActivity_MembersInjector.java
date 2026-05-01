package com.qrmaster.ui.details;

import com.qrmaster.domain.repository.QRCodeRepository;
import dagger.MembersInjector;
import dagger.internal.DaggerGenerated;
import dagger.internal.InjectedFieldSignature;
import dagger.internal.QualifierMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class DetailsActivity_MembersInjector implements MembersInjector<DetailsActivity> {
  private final Provider<QRCodeRepository> repositoryProvider;

  public DetailsActivity_MembersInjector(Provider<QRCodeRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  public static MembersInjector<DetailsActivity> create(
      Provider<QRCodeRepository> repositoryProvider) {
    return new DetailsActivity_MembersInjector(repositoryProvider);
  }

  @Override
  public void injectMembers(DetailsActivity instance) {
    injectRepository(instance, repositoryProvider.get());
  }

  @InjectedFieldSignature("com.qrmaster.ui.details.DetailsActivity.repository")
  public static void injectRepository(DetailsActivity instance, QRCodeRepository repository) {
    instance.repository = repository;
  }
}
