package mainTER.exception;

/**
 * Exception for when we insert a data into checkpoint and the map given doesn't exist.
 */
public class CheckpointsMapDoesntExistException extends Exception{

    private final String message;

    public CheckpointsMapDoesntExistException(String mapName){
        this.message = "La map " + mapName + " n'existe pas dans la base de données.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
