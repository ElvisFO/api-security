package br.com.security.application.exceptionhandler.exception;

/**
 * @author Elvis Fernandes on 01/11/19
 */
public class ObjectNotFoundException extends RuntimeException {

    private Object[] params;

    public ObjectNotFoundException(String msgCode, Object[] params) {
        super(msgCode);
        this.params = params;
    }

    public ObjectNotFoundException(String msgCode) {
        super(msgCode);
        this.params = new Object[]{};
    }

    public Object[] getParams() {
        return params;
    }
}
