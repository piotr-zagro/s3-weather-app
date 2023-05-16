package pl.xinsight.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import pl.xinsight.task.configuration.TestAwsConfiguration;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.transfer.s3.S3TransferManager;
import software.amazon.awssdk.transfer.s3.model.CompletedDirectoryUpload;
import software.amazon.awssdk.transfer.s3.model.DirectoryUpload;
import software.amazon.awssdk.transfer.s3.model.UploadDirectoryRequest;

import java.nio.file.Paths;


@SpringBootTest
@ContextConfiguration(classes = TestAwsConfiguration.class)
@ActiveProfiles("test")
public class BaseTest {

    private static final Logger logger = LoggerFactory.getLogger(BaseTest.class);

    public static void createS3Bucket(S3Client s3Client, String bucketName) {
        s3Client.createBucket(b -> b.bucket(bucketName));
    }

    public static void loadDataToS3Bucket(S3AsyncClient s3AsyncClient, String bucketName, String testDataPath) {
        try (var transferManager = S3TransferManager.builder().s3Client(s3AsyncClient).build()) {
            var directoryUpload = transferManager.uploadDirectory(UploadDirectoryRequest.builder()
                    .source(Paths.get(testDataPath))
                    .bucket(bucketName)
                    .build());

            CompletedDirectoryUpload completedDirectoryUpload = directoryUpload.completionFuture().join();
            completedDirectoryUpload.failedTransfers().forEach(fail ->
                    logger.error("Object [{}] failed to transfer", fail.toString()));
        }

    }

}
