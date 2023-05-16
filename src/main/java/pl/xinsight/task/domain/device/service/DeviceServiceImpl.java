package pl.xinsight.task.domain.device.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.xinsight.task.domain.device.model.DeviceData;
import pl.xinsight.task.domain.device.model.SensorType;
import pl.xinsight.task.domain.device.repository.DeviceRepository;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class DeviceServiceImpl implements DeviceService {

    private final DeviceRepository deviceRepository;

    @Override
    public DeviceData getDeviceData(String deviceId, LocalDate date, String sensorType) {
        return deviceRepository.getMeasurementsForDevice(deviceId, date, SensorType.fromString(sensorType));
    }
}
