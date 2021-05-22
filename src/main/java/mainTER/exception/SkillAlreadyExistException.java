package mainTER.exception;

/**
 * Exception for when the data for the skill we want to insert already exist.
 */
public class SkillAlreadyExistException extends Exception{

    private final String message;

    public SkillAlreadyExistException(String nameCharacter, String nameSkill){
        this.message = "The skill " + nameSkill + " already exist for the character " + nameCharacter+".";
    }


    @Override
    public String getMessage(){
        return message;
    }

}
