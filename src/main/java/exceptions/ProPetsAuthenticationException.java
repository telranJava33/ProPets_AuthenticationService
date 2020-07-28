package exceptions;

import org.springframework.http.HttpStatus;

public class ProPetsAuthenticationException extends RuntimeException{
    private HttpStatus status;
    private String clientMessage;

    public ProPetsAuthenticationException(HttpStatus status, String clientMessage) {
        this.status = status;
        this.clientMessage = clientMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getClientMessage() {
        return clientMessage;
    }

    public void setClientMessage(String clientMessage) {
        this.clientMessage = clientMessage;
    }
}
