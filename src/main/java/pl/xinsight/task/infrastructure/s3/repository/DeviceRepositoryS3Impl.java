package pl.xinsight.task.infrastructure.s3.repository;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import pl.xinsight.task.domain.device.exception.NotFoundDataException;
import pl.xinsight.task.domain.device.model.DeviceData;
import pl.xinsight.task.domain.device.model.Measurement;
import pl.xinsight.task.domain.device.model.SensorData;
import pl.xinsight.task.domain.device.model.SensorType;
import pl.xinsight.task.domain.device.repository.DeviceRepository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

@Repository
@RequiredArgsConstructor
public class DeviceRepositoryS3Impl implements DeviceRepository {

    private static final String TEMPORARY_KEY_TEMPLATE = "iotbackend/%s/%s/%s.csv";
    private static final String ARCHIVED_KEY_TEMPLATE = "iotbackend/%s/%s/historical.zip";
    private static final String ARCHIVED_FILENAME_TEMPLATE = "historical/%s.csv";
    public static final String NO_SUCH_KEY_ERROR_CODE = "NoSuchKey";
    public static final String CSV_COLUMN_SEPARATOR = ";";
    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    @Override
    public DeviceData getMeasurementsForDevice(String deviceId, LocalDate date, SensorType sensorType) {
        List<String> sensorTypes = getSensorTypes(sensorType);

        List<SensorData> sensorDataList = sensorTypes.stream()
                .map(sensor -> SensorData.builder()
                        .sensorType(SensorType.fromString(sensor))
                        .measurements(getMeasurements(deviceId, date, sensor))
                        .build()).toList();

        return DeviceData.builder()
                .deviceId(deviceId)
                .sensorData(sensorDataList)
                .build();
    }

    private List<String> getSensorTypes(SensorType sensorType) {
        if (Objects.nonNull(sensorType)) {
            return Collections.singletonList(sensorType.toString().toLowerCase());
        } else {
            return Arrays.stream(SensorType.values())
                    .map(s -> s.toString().toLowerCase())
                    .collect(Collectors.toList());
        }
    }

    private List<Measurement> getMeasurements(String deviceId, LocalDate date, String sensorType) {
        InputStream stream = getMeasurementsAsStream(deviceId, date, sensorType);
        return mapS3Data(stream);
    }

    private InputStream getMeasurementsAsStream(String deviceId, LocalDate date, String sensorType) {
        Optional<S3ObjectInputStream> data = getMeasurementsTemporaryObject(deviceId, date, sensorType);

        if (data.isPresent()) {
            return data.get();
        }

        Optional<ZipInputStream> archivedData = getMeasurementsArchiveObject(deviceId, date, sensorType);

        if (archivedData.isPresent()) {
            return archivedData.get();
        } else {
            throw new NotFoundDataException(deviceId, date, sensorType);
        }
    }

    private Optional<ZipInputStream> getMeasurementsArchiveObject(String deviceId, LocalDate date, String sensorType) {
        String archivedFileName = String.format(ARCHIVED_FILENAME_TEMPLATE, date.toString());
        Optional<S3ObjectInputStream> optionalS3ObjectInputStream = getS3Object(String.format(ARCHIVED_KEY_TEMPLATE, deviceId, sensorType));

        if(optionalS3ObjectInputStream.isEmpty()) {
            return Optional.empty();
        }

        ZipInputStream zipInputStream = new ZipInputStream(optionalS3ObjectInputStream.get());

        ZipEntry entry;
        try {
            while ((entry = zipInputStream.getNextEntry()) != null) {
                if (entry.isDirectory()) {
                    entry = zipInputStream.getNextEntry();
                }
                if (archivedFileName.equals(entry.getName())) {
                    return Optional.of(zipInputStream);
                }
                zipInputStream.closeEntry();
            }
        } catch (Exception e) {
            return Optional.empty();
        }

        return Optional.empty();
    }

    private Optional<S3ObjectInputStream> getMeasurementsTemporaryObject(String deviceId, LocalDate date, String sensorType) {
        String key = String.format(TEMPORARY_KEY_TEMPLATE, deviceId, sensorType, date.toString());
        return getS3Object(key);
    }

    private Optional<S3ObjectInputStream> getS3Object(String key) {
        S3Object s3Object;
        try {
            s3Object = amazonS3.getObject(bucketName, key);
        } catch (AmazonS3Exception e) {
            if (NO_SUCH_KEY_ERROR_CODE.equals(e.getErrorCode())) {
                return Optional.empty();
            }

            throw e;
        }

        return Optional.ofNullable(s3Object.getObjectContent());
    }

    private List<Measurement> mapS3Data(InputStream inputStream) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        List<String> lines = new ArrayList<>();
        String line;
        try {
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return lines.stream()
                .map(this::mapCSVRowToMeasurement)
                .toList();
    }

    private Measurement mapCSVRowToMeasurement(String row) {
        String[] splittedRow = row.split(CSV_COLUMN_SEPARATOR);
        return Measurement.builder()
                .dateTime(LocalDateTime.parse(splittedRow[0]))
                .value(Float.parseFloat(splittedRow[1].replace(',', '.')))
                .build();
    }
}
