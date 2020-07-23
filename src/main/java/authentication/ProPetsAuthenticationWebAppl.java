package authentication;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

//@ComponentScan(basePackages = "telran.propets.authentication")
//@Configuration
//@EnableAutoConfiguration
@SpringBootApplication
public class ProPetsAuthenticationWebAppl extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(ProPetsAuthenticationWebAppl.class, args);
    }
}
