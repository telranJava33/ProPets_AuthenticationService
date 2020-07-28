package authentication.Service;

import authentication.AuthResponse.AuthResponse;
import authentication.AuthResponse.AuthResponseToken;

import javax.naming.AuthenticationException;
import java.io.Serializable;

public interface IAuthentication extends Serializable {
    AuthResponse validateToken(String token);
    String refreshToken(String token);
    AuthResponseToken generateToken(String email);
    Boolean validateEmail(String email, String token);
    AuthResponse validateTokenAndEmail(String token, String email);
}
