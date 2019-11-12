package br.com.security.application.service.impl;

import br.com.security.application.model.UserEntity;
import br.com.security.application.repository.UserEntityRepository;
import br.com.security.application.security.UserSS;
import br.com.security.application.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author Elvis Fernandes on 22/10/19
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserEntityService service;

    @Autowired
    public UserDetailsServiceImpl(UserEntityService service) {
        this.service = service;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        UserEntity userEntity = this.service.findByEmailIgnoreCaseAndActiveTrue(email).orElseThrow(() -> new UsernameNotFoundException(email));

        return new UserSS(userEntity);
    }
}
