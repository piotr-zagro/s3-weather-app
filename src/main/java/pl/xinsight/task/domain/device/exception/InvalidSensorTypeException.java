package pl.xinsight.task.domain.device.exception;

import lombok.Value;

import java.time.LocalDate;

@Value
public class InvalidSensorTypeException extends RuntimeException {
    String sensorType;
    public InvalidSensorTypeException(String sensorType) {
        super();
        this.sensorType = sensorType;
    }
}
