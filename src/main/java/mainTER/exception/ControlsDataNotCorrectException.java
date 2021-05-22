package mainTER.exception;

/**
 * Exception for when the inserted values for the table controls aren't correct.
 */
public class ControlsDataNotCorrectException extends Exception{

    private final String message;

    public ControlsDataNotCorrectException(){
        this.message = "Les contrôles insérées ne sont pas valides.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
