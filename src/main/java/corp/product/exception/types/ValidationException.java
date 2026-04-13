package corp.product.exception.types;

public class ValidationException extends RuntimeException {
    public ValidationException(String message) {
        super(message);
    }
}