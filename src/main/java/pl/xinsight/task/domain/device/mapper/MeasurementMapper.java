package pl.xinsight.task.domain.device.mapper;

import org.mapstruct.Mapper;
import pl.xinsight.task.application.response.MeasurementDTO;
import pl.xinsight.task.domain.device.model.Measurement;

import java.util.List;

@Mapper
public interface MeasurementMapper {
    MeasurementDTO toMeasurementDto(Measurement measurements);
    List<MeasurementDTO> toMeasurementDto(List<Measurement> measurements);
}
