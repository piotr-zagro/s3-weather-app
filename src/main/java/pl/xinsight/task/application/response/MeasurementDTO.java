package pl.xinsight.task.application.response;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class MeasurementDTO {
    LocalDateTime dateTime;
    float value;
}
