package mainTER.exception;

/**
 * Exception for when we try to insert data that aren't correct into the table Skill.
 */
public class SkillDataNotCorrectException extends Exception{

    private final String message;

    public SkillDataNotCorrectException(){
        this.message = "The data given aren't correct.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
