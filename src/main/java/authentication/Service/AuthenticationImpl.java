package authentication.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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
             return new AuthResponse(false, AuthenticationReturnCode.EXPERAION_SESSION.name());
         } catch (JWTVerificationException exception){
             return new AuthResponse(false, AuthenticationReturnCode.INVALID_TOKEN.name());
        }
        return new AuthResponse(true, AuthenticationReturnCode.OK.name());
    }

    @Override
    public String refreshToken(String token) {

        return null;
    }

    @Override
    public String generateToken(String email) {
        String token = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            token = JWT.create()
                    .withClaim("email", email)
                    .withExpiresAt(new Date(new Date().getTime() + (1000 * 60 * 60 * 24)))
                    .sign(algorithm);
        } catch (JWTCreationException exception){
           // throw new ProPetsAuthenticationException(HttpStatus.UNAUTHORIZED, UsersReturnCode.NEED_TO_SIGN_IN.name());
        }
        return token;
    }

    @Override
    public Boolean validateEmail(String email, String token) {
        String emailFromToken = JWT.decode(token).getClaim("email").asString();
        return email.equalsIgnoreCase(emailFromToken);
    }
}
