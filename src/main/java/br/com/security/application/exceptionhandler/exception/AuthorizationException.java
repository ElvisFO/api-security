package br.com.security.application.exceptionhandler.exception;

/**
 * @author Elvis Fernandes on 06/11/19
 */
public class AuthorizationException extends RuntimeException {

    private Object[] params;

    public AuthorizationException(String msgCode, Object[] params) {
        super(msgCode);
        this.params = params;
    }

    public AuthorizationException(String msgCode) {
        super(msgCode);
        this.params = new Object[]{};
    }

    public Object[] getParams() {
        return params;
    }
}
