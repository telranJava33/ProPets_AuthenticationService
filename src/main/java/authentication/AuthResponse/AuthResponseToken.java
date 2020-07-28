package authentication.AuthResponse;

public class AuthResponseToken {
    boolean status;
    String message;
    String token;

    public AuthResponseToken(boolean status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public boolean Status() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String getToken() {
        return token;
    }
}
