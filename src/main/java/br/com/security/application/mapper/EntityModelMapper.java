package br.com.security.application.mapper;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;

import java.util.List;

/**
 * @author Elvis Fernandes on 29/10/19
 */
public interface EntityModelMapper <T, DTO> {

    void configure(ModelMapper modelMapper);

    T convertToEntity(DTO dto);

    DTO convertToDto(T entity);

    List<T> convertToEntityList(List<DTO> dtos);

    List<DTO> convertToDtoList(List<T> entityList);

    Converter<DTO, T> toEntityConverter();

    Converter<T, DTO> toDtoConverter();
}
