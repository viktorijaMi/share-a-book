package mk.ukim.finki.emt.authentication.domain.exceptions;

public class PasswordDoNotMatchException extends RuntimeException{
    public PasswordDoNotMatchException(String m){
        super(m);
    }
}
