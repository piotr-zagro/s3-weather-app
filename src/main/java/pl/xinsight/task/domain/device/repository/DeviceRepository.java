package pl.xinsight.task.domain.device.repository;

import pl.xinsight.task.domain.device.model.DeviceData;
import pl.xinsight.task.domain.device.model.SensorType;

import java.time.LocalDate;

public interface DeviceRepository {
    DeviceData getMeasurementsForDevice(String deviceId, LocalDate date, SensorType sensorType);
}
