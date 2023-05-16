package pl.xinsight.task;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;
import software.amazon.awssdk.services.s3.S3AsyncClient;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.ListObjectsRequest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TaskApplicationTest extends BaseTest {

    private static final String BUCKET_NAME = "iotbackend";
    private static final String TEST_DATA_PATH = "src/test/resources/iotbackend/";

    @Autowired
    private S3Client s3Client;

    @Autowired
    private S3AsyncClient s3AsyncClient;

//    @Autowired
//    private ExampleService exampleService;
//
//    @Test
//    public void shouldReturnContentFromExampleService() throws IOException {
//        //given
//        createS3Bucket(s3Client, BUCKET_NAME);
//        loadDataToS3Bucket(s3AsyncClient, BUCKET_NAME, TEST_DATA_PATH);
//        var expectedContentOfFileFromS3 =
//                Files.readString(Paths.get(TEST_DATA_PATH + "dockan/temperature/2019-01-17.csv"));
//
//        //when
//        var content = exampleService.exampleGetObjectFromS3();
//
//        //then
//        assertThat(s3Client.listBuckets().buckets().stream().anyMatch(b -> b.name().equals(BUCKET_NAME)))
//                .as("New bucket was created")
//                .isTrue();
//
//        assertThat(s3Client.listObjects(ListObjectsRequest.builder().bucket(BUCKET_NAME).build()).contents())
//                .as("Bucket is not empty")
//                .isNotEmpty();
//
//        assertThat(content)
//                .as("Response contains content of example file from S3")
//                .isEqualTo(expectedContentOfFileFromS3);
//    }

}
