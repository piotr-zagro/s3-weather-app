package pl.xinsight.task.application.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DeviceDataDTO {

    String deviceId;
    List<SensorDataDTO> sensorData;

}
