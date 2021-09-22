package mk.ukim.finki.emt.authentication.domain.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class PasswordDoNotMatchException extends RuntimeException{
    public PasswordDoNotMatchException(String m){
        super(m);
    }
}
