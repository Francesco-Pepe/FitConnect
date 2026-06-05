package exception;

public class InvalidExerciseException extends RuntimeException {
    public InvalidExerciseException(String message) {
        super(message);
    }
    public InvalidExerciseException(String message,Throwable cause){super(message, cause);}
}
