package pl.xinsight.task.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.localstack.LocalStackContainer;
import org.testcontainers.utility.DockerImageName;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;

@TestConfiguration
public class TestAwsConfiguration {

    private static final DockerImageName LOCALSTACK_IMAGE =
            DockerImageName.parse("localstack/localstack:0.12.8");

    @Bean
    public LocalStackContainer localStackContainer() {
        var localStackContainer = new LocalStackContainer(LOCALSTACK_IMAGE)
                .withServices(LocalStackContainer.Service.S3).withSharedMemorySize(52428800L);
        localStackContainer.start();

        return localStackContainer;
    }

    @Bean
    @Autowired
    public S3Client s3Client(LocalStackContainer localstack) {
        return S3Client
                .builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
                        )
                )
                .region(Region.of(localstack.getRegion()))
                .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
                .build();
    }

    @Bean
    @Autowired
    public S3AsyncClient s3AsyncClient(LocalStackContainer localstack) {
        return S3AsyncClient
                .builder()
                .credentialsProvider(
                        StaticCredentialsProvider.create(
                                AwsBasicCredentials.create(localstack.getAccessKey(), localstack.getSecretKey())
                        )
                )
                .region(Region.of(localstack.getRegion()))
                .endpointOverride(localstack.getEndpointOverride(LocalStackContainer.Service.S3))
                .build();
    }
}
