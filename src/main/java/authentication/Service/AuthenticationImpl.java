package authentication.Service;

import authentication.AuthResponse.AuthResponseToken;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import exceptions.ProPetsAuthenticationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import authentication.AuthResponse.AuthResponse;
import authentication.AuthResponse.AuthenticationReturnCode;
import java.util.Date;

@Service
public class AuthenticationImpl implements IAuthentication {
    private final String secret = "ProPets";

    @Override
    public AuthResponse validateToken(String token) {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .build();
         try {
             verifier.verify(token);
         } catch (TokenExpiredException e){
             return new AuthResponse(false, AuthenticationReturnCode.EXPIRATION_SESSION.name());
         } catch (JWTVerificationException exception){
             return new AuthResponse(false, AuthenticationReturnCode.INVALID_TOKEN.name());
        }
        return new AuthResponse(true, AuthenticationReturnCode.OK.name());
    }

    @Override
    public String refreshToken(String token) {
        AuthResponse isValid = validateToken(token);
        if(!isValid.getStatus()){
            throw new ProPetsAuthenticationException(HttpStatus.UNAUTHORIZED, isValid.getMessage());
        }
        String email = JWT.decode(token).getClaim("email").asString();
        AuthResponseToken newToken = generateToken(email);
        if(!newToken.Status()){
            throw new ProPetsAuthenticationException(HttpStatus.INTERNAL_SERVER_ERROR, newToken.getMessage());
        }
        return newToken.getToken();
    }

    @Override
    public AuthResponseToken generateToken(String email) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withClaim("email", email)
                    .withExpiresAt(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            return new AuthResponseToken(false, AuthenticationReturnCode.CREATION_TOKEN_FAILED.name(), null);
        }
        return new AuthResponseToken(true, AuthenticationReturnCode.OK.name(), token);
    }

    @Override
    public Boolean validateEmail(String email, String token) {
        String emailFromToken = JWT.decode(token).getClaim("email").asString();
        return email.equalsIgnoreCase(emailFromToken);
    }

    @Override
    public AuthResponse validateTokenAndEmail(String token, String email) {
        AuthResponse authResponse = validateToken(token);
        if(!authResponse.getStatus()){
            return authResponse;
        }
        if(!validateEmail(email, token)){
            return new AuthResponse(false, AuthenticationReturnCode.INVALID_EMAIL.name());
        }
        return new AuthResponse(true, AuthenticationReturnCode.OK.name());
    }
}
