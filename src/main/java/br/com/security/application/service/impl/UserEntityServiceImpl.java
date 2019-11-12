package br.com.security.application.service.impl;

import br.com.security.application.commons.SecurityUtils;
import br.com.security.application.exceptionhandler.exception.AuthorizationException;
import br.com.security.application.model.UserEntity;
import br.com.security.application.repository.UserEntityRepository;
import br.com.security.application.security.UserSS;
import br.com.security.application.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static br.com.security.application.model.enums.Profile.ADMIN;

/**
 * @author Elvis Fernandes on 05/11/19
 */
@Service
public class UserEntityServiceImpl implements UserEntityService {

    private final UserEntityRepository repository;

    @Autowired
    public UserEntityServiceImpl(UserEntityRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<UserEntity> findById(Long id) {
        UserSS user = SecurityUtils.getCurrentUser().orElse(null);

        if(user == null || !user.hasRole(ADMIN) && !id.equals(user.getId())) {
            throw new AuthorizationException("error.forbidden.notfound");
        }

        return this.repository.findById(id);
    }

    @Override
    public List<UserEntity> findAll() {
        return this.findAll();
    }

    @Override
    public Optional<UserEntity> findByEmailIgnoreCaseAndActiveTrue(String email) {
        return this.repository.findByEmailIgnoreCaseAndActiveTrue(email);
    }

    @Override
    public Optional<UserEntity> findByEmailIgnoreCase(String email) {
        return this.repository.findByEmailIgnoreCase(email);
    }

    @Override
    public void deleteById(Long id) {
        this.repository.deleteById(id);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        return this.repository.save(entity);
    }
}
