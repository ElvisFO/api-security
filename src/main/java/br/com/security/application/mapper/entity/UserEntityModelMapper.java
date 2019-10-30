package br.com.security.application.mapper.entity;

import br.com.security.application.dto.UserDTO;
import br.com.security.application.mapper.impl.AbstractEntityModelMapper;
import br.com.security.application.model.UserEntity;
import br.com.security.application.model.enums.Profile;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * @author Elvis Fernandes on 29/10/19
 */
@Component
public class UserEntityModelMapper extends AbstractEntityModelMapper<UserEntity, UserDTO> {

    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    protected UserEntityModelMapper(ModelMapper modelMapper, BCryptPasswordEncoder passwordEncoder) {
        super(modelMapper);
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Converter<UserDTO, UserEntity> toEntityConverter() {
        return mappingContext ->  {
            UserEntity destination = mappingContext.getDestination();
            destination.setPassword(this.passwordEncoder.encode(mappingContext.getSource().getPassword()));
            if(!destination.getProfiles().contains(Profile.USER)) {
                destination.addProfile(Profile.USER);
            }
            return destination;
        };
    }

    @Override
    public Converter<UserEntity, UserDTO> toDtoConverter() {
        return mappingContext ->  {
            UserDTO destination = mappingContext.getDestination();
            destination.setPassword(null);
            return destination;
        };
    }
}
