package mainTER.exception;

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
