package mk.ukim.finki.emt.authentication.domain.exceptions;

public class InvalidCredentialsException extends RuntimeException{

    public InvalidCredentialsException(String m){
        super(m);
    }
}
