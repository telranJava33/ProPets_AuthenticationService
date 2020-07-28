package authentication;

import authentication.AuthResponse.AuthResponse;
import authentication.AuthResponse.AuthResponseToken;
import authentication.AuthResponse.AuthenticationReturnCode;
import authentication.Service.IAuthentication;
import authentication.api.ApiConstants;
import exceptions.ProPetsAuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    IAuthentication authentication;

    @GetMapping(value = ApiConstants.VALIDATE_TOKEN)
    public AuthResponse validateToken (HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return authentication.validateToken(token);
    }

    @PutMapping(value = ApiConstants.REFRESH_TOKEN)
    public ResponseEntity<String> refreshToken (HttpServletRequest request){
        String token = request.getHeader("Authorization");
        String newToken = null;
        try {
            newToken = authentication.refreshToken(token);
        } catch (ProPetsAuthenticationException e) {
            return new ResponseEntity<>(e.getClientMessage(), e.getStatus());
        }
        return new ResponseEntity<>(newToken, HttpStatus.OK);
    }

    @GetMapping(value = ApiConstants.GENERATE_TOKEN + "{email}")
    public AuthResponseToken generateToken (@PathVariable("email") String email){
        return authentication.generateToken(email);
    }

    @GetMapping(value = ApiConstants.VALIDATE_EMAIL + "{email}")
    public Boolean validateEmail (@PathVariable("email") String email, HttpServletRequest request){
        String token = request.getHeader("Authorization");
        return authentication.validateEmail(email, token);
    }

    @GetMapping(value = ApiConstants.VALIDATE_TOKEN + "{email}")
    public AuthResponse validateTokenAndEmail(HttpServletRequest request, @PathVariable("email") String email){
        String token = request.getHeader("Authorization");
        return authentication.validateTokenAndEmail(token, email);
    }
}