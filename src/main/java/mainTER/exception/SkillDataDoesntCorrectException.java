package mainTER.exception;

public class SkillDataDoesntCorrectException extends Exception{

    private final String message;

    public SkillDataDoesntCorrectException(){
        this.message = "The data given aren't correct.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
