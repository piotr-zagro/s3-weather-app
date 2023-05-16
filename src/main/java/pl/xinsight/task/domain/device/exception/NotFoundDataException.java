package pl.xinsight.task.domain.device.exception;

import lombok.Value;

import java.time.LocalDate;

@Value
public class NotFoundDataException extends RuntimeException {
    String deviceId;
    LocalDate date;
    String sensorType;
    public NotFoundDataException(String deviceId, LocalDate date, String sensorType) {
        super();
        this.deviceId = deviceId;
        this.date = date;
        this.sensorType = sensorType;
    }
}
