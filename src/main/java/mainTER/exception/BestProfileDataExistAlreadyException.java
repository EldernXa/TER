package mainTER.exception;

public class BestProfileDataExistAlreadyException extends Exception{

    private final String message;

    public BestProfileDataExistAlreadyException(String mapName){
        message = "Le meilleurs temps pour la map " + mapName + " existe déjà.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
