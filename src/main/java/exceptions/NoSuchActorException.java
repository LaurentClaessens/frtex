package actors.exceptions;

/**
 * Thrown to indicate an error during the creation of a new actor of unknown type in a specified
 * {@link actors.ActorSystem actor system}.
 *
 */
public class NoSuchActorException extends RuntimeException {

    public NoSuchActorException() {
    }

    public NoSuchActorException(String message) {
        super(message);
    }

    public NoSuchActorException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchActorException(Throwable cause) {
        super(cause);
    }
}
