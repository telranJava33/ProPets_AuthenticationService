package authentication.AuthResponse;

import org.springframework.http.HttpStatus;

public class AuthResponse {
    boolean status;
    String message;

    public AuthResponse(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
