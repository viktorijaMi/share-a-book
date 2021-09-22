package mk.ukim.finki.emt.authentication.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.annotation.security.RunAs;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String m){
        super(m);
    }
}
