package mainTER.exception;

/**
 * Exception for when we try to insert data into a map and the character given doesn't exist.
 */
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
