package br.com.security.application.service.impl;

import br.com.security.application.model.UserEntity;
import br.com.security.application.model.enums.Profile;
import br.com.security.application.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author Elvis Fernandes on 20/10/19
 */
@Service
public class DBService {

    private final BCryptPasswordEncoder bCrypt;
    private final UserEntityRepository repository;

    @Autowired
    public DBService(BCryptPasswordEncoder bCrypt, UserEntityRepository repository) {
        this.bCrypt = bCrypt;
        this.repository = repository;
    }

    public void instantiateTestDatabase() {

        UserEntity user1 = new UserEntity();
        user1.setName("User1");
        user1.setEmail("test1@test.com");
        user1.setPassword(bCrypt.encode("123"));
        user1.addProfile(Profile.ADMIN);

        UserEntity user2 = new UserEntity();
        user2.setName("User2");
        user2.setEmail("test2@test.com");
        user2.setPassword(bCrypt.encode("1234"));


        this.repository.saveAll(Arrays.asList(user1, user2));
    }
}
