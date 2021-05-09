package mainTER.exception;

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
