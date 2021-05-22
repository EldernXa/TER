package mainTER.exception;

/**
 * Exception for when the table Controls is empty.
 */
public class ControlsDataGetException extends Exception {

    private final String message;

    public ControlsDataGetException(){
        this.message = "Les informations des controles n'existent pas !";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
