package pl.xinsight.task.domain.device.service;

import pl.xinsight.task.domain.device.model.DeviceData;

import java.time.LocalDate;

public interface DeviceService {
    DeviceData getDeviceData(String deviceId, LocalDate date, String sensorType);
}
