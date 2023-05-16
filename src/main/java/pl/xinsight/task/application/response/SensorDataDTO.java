package pl.xinsight.task.application.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class SensorDataDTO {
    String sensorType;
    List<MeasurementDTO> measurements;
}
