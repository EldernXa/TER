package mainTER.exception;

/**
 * Exception for when the map already exist.
 */
public class MapAlreadyExistException extends Exception{

    private final String message;

    public MapAlreadyExistException(String mapName){
        this.message = "La map " + mapName + " existe déjà.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
