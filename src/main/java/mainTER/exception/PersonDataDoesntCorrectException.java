package mainTER.exception;

public class PersonDataDoesntCorrectException extends Exception{

    private final String message;

    public PersonDataDoesntCorrectException(String nameCharacter){
        this.message = "One data inserted for " + nameCharacter + " isn't correct.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
