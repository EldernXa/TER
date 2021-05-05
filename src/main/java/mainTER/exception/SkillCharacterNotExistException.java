package mainTER.exception;

public class SkillCharacterNotExistException extends Exception{

    private final String message;

    public SkillCharacterNotExistException(String nameCharacter){
        this.message = "Le personnage " + nameCharacter + " n'existe pas.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
