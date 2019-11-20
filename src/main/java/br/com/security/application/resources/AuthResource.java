package br.com.security.application.resources;

import br.com.security.application.commons.SecurityUtils;
import br.com.security.application.exceptionhandler.exception.ObjectNotFoundException;
import br.com.security.application.security.JWTUtil;
import br.com.security.application.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Elvis Fernandes on 19/11/19
 */
@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

    private final JWTUtil jwtUtil;

    @Autowired
    public AuthResource(JWTUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
    public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
        UserSS user = SecurityUtils.getCurrentUser().orElseThrow(() -> new ObjectNotFoundException("error.user.notfound"));
        String token = (String) this.jwtUtil.generateTokenBody(user.getUsername()).get("token");
        response.addHeader("Authorization", "Bearer ".concat(token));
        return ResponseEntity.noContent().build();
    }
}
