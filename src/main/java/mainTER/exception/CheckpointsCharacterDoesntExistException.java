package mainTER.exception;

/**
 * Exception for when the Character doesn't exist for the insert in checkpoint.
 */
public class CheckpointsCharacterDoesntExistException extends Exception{

    private final String message;

    public CheckpointsCharacterDoesntExistException(String nameCharacter){
        this.message = "Le personnage " + nameCharacter + " n'existe pas dans la base de données.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
