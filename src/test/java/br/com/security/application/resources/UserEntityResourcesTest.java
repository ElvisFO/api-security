package br.com.security.application.resources;

import br.com.security.application.repository.UserEntityRepository;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.springframework.http.HttpMethod.DELETE;
import static org.springframework.http.HttpMethod.GET;

/**
 * @author Elvis Fernandes on 30/10/19
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserEntityResourcesTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Autowired
    private UserEntityRepository userRepository;

    private HttpEntity<Void> userHeader;
    private HttpEntity<Void> adminHeader;
    private HttpEntity<Void> wrongHeader;

    @Before
    public void configUserHeaders() {
        String login = "{\"email\": \"test2@test.com\", \"password\": \"1234\"}";
        HttpHeaders headers = this.restTemplate.postForEntity("/login", login, String.class).getHeaders();
        this.userHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configAdminHeaders() {
        String login = "{\"email\": \"test1@test.com\", \"password\": \"123\"}";
        HttpHeaders headers = this.restTemplate.postForEntity("/login", login, String.class).getHeaders();
        this.adminHeader = new HttpEntity<>(headers);
    }

    @Before
    public void configWrongHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization","11111");
        this.wrongHeader = new HttpEntity<>(headers);
    }

    @Test
    public void listUsersWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = this.restTemplate.exchange("/users", GET, wrongHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void getUsersByIdWhenTokenIsIncorrectShouldReturnStatusCode403() {
        ResponseEntity<String> response = this.restTemplate.exchange("/users/1", GET, wrongHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(403);
    }

    @Test
    public void listUsersWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = this.restTemplate.exchange("/users",GET, userHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getUsersByIdWhenTokenIsCorrectShouldReturnStatusCode200() {
        ResponseEntity<String> response = this.restTemplate.exchange("/users/1",GET, userHeader, String.class);
        Assertions.assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void deleteWhenUserHasRoleAdminAndUserExistsShouldReturnStatusCode204() {
        ResponseEntity<String> exchange = this.restTemplate.exchange("/users/1", DELETE, adminHeader, String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(204);
    }

    @Test
    public void deleteWhenUserDoesNotHaveRoleAdminShouldReturnStatusCode403() {
        ResponseEntity<String> exchange = this.restTemplate.exchange("/users/1", DELETE, userHeader, String.class);
        Assertions.assertThat(exchange.getStatusCodeValue()).isEqualTo(403);
    }
}
