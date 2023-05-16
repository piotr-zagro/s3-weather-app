package pl.xinsight.task.domain.device.mapper;

import org.mapstruct.Mapper;
import pl.xinsight.task.application.response.DeviceDataDTO;
import pl.xinsight.task.domain.device.model.DeviceData;

@Mapper(uses = {MeasurementMapper.class})
public interface DeviceMapper {

    DeviceDataDTO toDto(DeviceData deviceData);

}
