package pl.xinsight.task.application.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.xinsight.task.application.response.DeviceDataDTO;
import pl.xinsight.task.domain.device.mapper.DeviceMapper;
import pl.xinsight.task.domain.device.model.DeviceData;
import pl.xinsight.task.domain.device.service.DeviceService;

import java.time.LocalDate;

@RestController
@RequestMapping(ApiPaths.DEVICE_PATH)
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;
    private final DeviceMapper deviceMapper;

    @GetMapping("/{deviceId}/date/{date}")
    public ResponseEntity<DeviceDataDTO> deviceData(@PathVariable String deviceId,
                                                    @PathVariable
                                                    @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                    LocalDate date) {
        DeviceData deviceData = deviceService.getDeviceData(deviceId, date, null);
        return ResponseEntity.ok(deviceMapper.toDto(deviceData));
    }

    @GetMapping("/{deviceId}/date/{date}/sensor/{sensorType}")
    public ResponseEntity<DeviceDataDTO> deviceSensorData(@PathVariable String deviceId,
                                                                @PathVariable
                                                                @DateTimeFormat(pattern = "yyyy-MM-dd")
                                                                LocalDate date,
                                                                @PathVariable String sensorType) {
        DeviceData deviceData = deviceService.getDeviceData(deviceId, date, sensorType);
        return ResponseEntity.ok(deviceMapper.toDto(deviceData));
    }
}
