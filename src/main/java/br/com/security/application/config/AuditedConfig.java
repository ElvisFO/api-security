package br.com.security.application.config;

import br.com.security.application.commons.SecurityUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.envers.repository.support.EnversRevisionRepositoryFactoryBean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Optional;

/**
 * @author Elvis Fernandes on 20/10/19
 */
@Configuration
@EnableJpaAuditing
@EnableJpaRepositories(basePackages = "br.com.security.application.repository", repositoryFactoryBeanClass = EnversRevisionRepositoryFactoryBean.class)
public class AuditedConfig {

    @Bean
    public AuditorAware<String> auditorAware() {
        return new SpringSecurityAuditorAware();
    }

    class SpringSecurityAuditorAware implements AuditorAware<String> {
        @Override
        public Optional<String> getCurrentAuditor() {
            return SecurityUtils.getCurrentUserName();
        }
    }
}
