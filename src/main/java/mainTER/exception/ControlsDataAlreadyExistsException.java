package mainTER.exception;

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
