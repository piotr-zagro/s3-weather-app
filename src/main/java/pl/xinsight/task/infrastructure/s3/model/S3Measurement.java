package pl.xinsight.task.infrastructure.s3.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class S3Measurement {
    LocalDateTime dateTime;
    float value;
}
