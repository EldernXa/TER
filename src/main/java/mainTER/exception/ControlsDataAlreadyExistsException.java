package mainTER.exception;

/**
 * Exception for when the data we want to insert for control already exist.
 */
public class ControlsDataAlreadyExistsException extends Exception{
    private final String message;

    public ControlsDataAlreadyExistsException(String data){
        this.message = "The " + data + " already exist in the database.";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
