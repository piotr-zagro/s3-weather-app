package pl.xinsight.task.domain.device.model;

import lombok.Builder;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@Builder
public class Measurement {
    LocalDateTime dateTime;
    float value;
}
