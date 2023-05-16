package pl.xinsight.task.application.resolver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import pl.xinsight.task.application.response.ErrorResponseDTO;
import pl.xinsight.task.domain.device.exception.InvalidSensorTypeException;
import pl.xinsight.task.domain.device.exception.NotFoundDataException;

@Slf4j
@RestControllerAdvice
public class ExceptionResolver {

    private static final String NOT_FOUND_DATA = "Not found data for prided query";
    private static final String INVALID_SENSOR_TYPE = "Invalid sensor type";
    private static final String INTERNAL_SERVER_ERROR = "Internal Server Error";

    @ExceptionHandler(InvalidSensorTypeException.class)
    public ResponseEntity<ErrorResponseDTO> invalidSensorTypeException(InvalidSensorTypeException exception) {
        log.info("Invalid sensorType: {}, message: {}", exception.getSensorType(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorResponseDTO.of(INVALID_SENSOR_TYPE));
    }

    @ExceptionHandler(NotFoundDataException.class)
    public ResponseEntity<ErrorResponseDTO> dataNotFoundException(NotFoundDataException exception) {
        log.info("Data not found for deviceId: {}, date: {}, sensorType: {}, message: {}", exception.getDeviceId(), exception.getDate().toString(), exception.getSensorType(), exception.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ErrorResponseDTO.of(NOT_FOUND_DATA));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> uncaughtException(Exception exception) {
        log.error("Uncaught exception: {}", exception.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ErrorResponseDTO.of(INTERNAL_SERVER_ERROR));
    }
}
