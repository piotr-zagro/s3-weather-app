package pl.xinsight.task.domain.device.model;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class DeviceData {
    String deviceId;
    List<SensorData> sensorData;

}
