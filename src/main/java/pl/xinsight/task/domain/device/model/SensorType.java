package pl.xinsight.task.domain.device.model;

import pl.xinsight.task.domain.device.exception.InvalidSensorTypeException;

import java.util.Objects;

public enum SensorType {
    TEMPERATURE,
    RAINFALL,
    HUMIDITY;

    public static SensorType fromString(String sensorType) {
        if (Objects.isNull(sensorType)) {
            return null;
        }

        String upperCase = sensorType.toUpperCase();

        SensorType sensor;
        try {
            sensor = SensorType.valueOf(upperCase);
        } catch (IllegalArgumentException exception) {
            throw new InvalidSensorTypeException(sensorType);
        }

        return sensor;
    }
}
