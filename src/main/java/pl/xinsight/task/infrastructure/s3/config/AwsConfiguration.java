package pl.xinsight.task.infrastructure.s3.config;

import com.amazonaws.auth.*;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class AwsConfiguration {

    @Value("${cloud.aws.credentials.access-key:undefined}")
    private String accessKey;
    @Value("${cloud.aws.credentials.secret-key:undefined}")
    private String secretKey;
    @Getter
    @Value("${cloud.aws.region.static}")
    private String region;

    @Bean
    public AmazonS3 s3Client() {
        return AmazonS3ClientBuilder.standard()
                .withCredentials(getAWSCredentialsProvider())
                .withRegion(region)
                .build();
    }

    private AWSCredentialsProvider getAWSCredentialsProvider() {
        if ("undefined".equals(accessKey)) {
            return new DefaultAWSCredentialsProviderChain();
        }

        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }


}
