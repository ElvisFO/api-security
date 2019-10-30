package br.com.security.application.config;

import br.com.security.application.model.UserEntity;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Elvis Fernandes on 29/10/19
 */
@Configuration
@EntityScan(basePackageClasses = UserEntity.class)
public class CoreConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
