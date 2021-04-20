package mainTER.exception;

public class PositionDirectoryDoesntExist extends Exception{

    private final String message;

    public PositionDirectoryDoesntExist(String namePosition){
        message = "The directory for the position " + namePosition + " doesn't exist.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
