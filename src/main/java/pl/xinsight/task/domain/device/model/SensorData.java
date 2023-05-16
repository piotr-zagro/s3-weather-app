package pl.xinsight.task.domain.device.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SensorData {
    SensorType sensorType;
    List<Measurement> measurements;
}
