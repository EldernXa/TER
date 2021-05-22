package mainTER.exception;

/**
 * Exception for when the character given doesn't exist when inserting for skill.
 */
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
