package mainTER.exception;

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
