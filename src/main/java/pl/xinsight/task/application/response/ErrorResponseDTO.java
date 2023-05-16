package pl.xinsight.task.application.response;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor(staticName = "of")
public class ErrorResponseDTO {
    String errorMessage;
}
