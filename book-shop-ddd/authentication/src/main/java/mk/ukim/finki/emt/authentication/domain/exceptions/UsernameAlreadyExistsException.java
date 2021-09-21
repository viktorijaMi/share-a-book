package mk.ukim.finki.emt.authentication.domain.exceptions;

public class UsernameAlreadyExistsException extends RuntimeException{
    public UsernameAlreadyExistsException(String m){
        super(m);
    }
}
