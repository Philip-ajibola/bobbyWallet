package africa.semicolon.ppay.infrastructure.adapter.input.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface DtoMappers {
    DtoMappers INSTANCE = Mappers.getMapper(DtoMappers.class);
}
