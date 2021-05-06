package mainTER.exception;

public class MapCharacterNotExistException extends Exception{

    private final String message;

    public MapCharacterNotExistException(String mapName, String nameCharacter){
        this.message = "Problème lors de la création de la map " + mapName + ", le personnage " + nameCharacter + " n'existe pas.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
