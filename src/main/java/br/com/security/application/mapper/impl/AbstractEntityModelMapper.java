package br.com.security.application.mapper.impl;

import br.com.security.application.mapper.EntityModelMapper;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Elvis Fernandes on 29/10/19
 */
@Slf4j
public abstract class AbstractEntityModelMapper<T, DTO> implements EntityModelMapper<T, DTO> {

    private final ModelMapper modelMapper;

    protected AbstractEntityModelMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }


    @Override
    public void configure(ModelMapper modelMapper) {
        log.info("Configuring {}.", getClass().getName());
        modelMapper.createTypeMap(getEntityClass(), getDtoClass()).setPostConverter(toDtoConverter());
        modelMapper.createTypeMap(getDtoClass(), getEntityClass()).setPostConverter(toEntityConverter());
    }

    @Override
    public T convertToEntity(DTO dto) {
        return modelMapper.map(dto, this.getEntityClass());
    }

    @Override
    public DTO convertToDto(T entity) {
        return modelMapper.map(entity, this.getDtoClass());
    }

    @Override
    public List<T> convertToEntityList(List<DTO> dtos) {
        return dtos.stream().map(this::convertToEntity).collect(Collectors.toList());
    }

    @Override
    public List<DTO> convertToDtoList(List<T> entityList) {
        return entityList.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private Class<T> getEntityClass() {
        return ((Class) ((ParameterizedType) this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[0]);
    }

    private Class<DTO> getDtoClass() {
        return ((Class) ((ParameterizedType) this.getClass().
                getGenericSuperclass()).getActualTypeArguments()[1]);
    }
}
