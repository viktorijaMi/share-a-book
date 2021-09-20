package mk.ukim.finki.emt.ordermanagement.domain.exceptions;

public class OrderItemAlreadyExistsException extends RuntimeException{

    public OrderItemAlreadyExistsException(String message) {
        super(message);
    }
}
