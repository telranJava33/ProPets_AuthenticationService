package authentication;

import authentication.AuthResponse.AuthResponse;
import authentication.Service.IAuthentication;
import authentication.api.ApiConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
public class AuthenticationController {

    @Autowired
    IAuthentication authentication;

    @GetMapping(value = ApiConstants.VALIDATE_TOKEN)
    public AuthResponse validateToken (HttpServletRequest request){
        String token = request.getHeader("authorization");
        return authentication.validateToken(token);
    }

    @PutMapping(value = ApiConstants.PEFRESH_TOKEN)
    public String refreshToken (HttpServletRequest request){
        String token = request.getHeader("authorization");
        return authentication.refreshToken(token);
    }

    @GetMapping(value = ApiConstants.GENERATE_TOKEN + "{email}")
    public String generateToken (@PathVariable("email") String email){
        return authentication.generateToken(email);
    }

    @GetMapping(value = ApiConstants.VALIDATE_EMAIL + "{email}")
    public Boolean validateEmail (@PathVariable("email") String email, HttpServletRequest request){
        String token = request.getHeader("authorization");
        return authentication.validateEmail(email, token);
    }
}