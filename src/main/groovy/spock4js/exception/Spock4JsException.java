package spock4js.exception;

@SuppressWarnings("serial")
public class Spock4JsException extends RuntimeException {

    public Spock4JsException() {
        super();
    }

    public Spock4JsException(String message, Throwable cause) {
        super(message, cause);
    }

    public Spock4JsException(String message) {
        super(message);
    }

    public Spock4JsException(Throwable cause) {
        super(cause);
    }

}
