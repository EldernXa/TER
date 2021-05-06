package mainTER.exception;

public class MapDataGetException extends Exception{

    private final String message;

    public MapDataGetException(String mapName){
        this.message = "La map " + mapName + " n'existe pas dans la base de données.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
