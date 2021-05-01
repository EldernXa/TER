package mainTER.exception;

public class PersonDataNotCorrectException extends Exception{

    private final String message;

    public PersonDataNotCorrectException(String nameCharacter){
        this.message = "One data inserted for " + nameCharacter + " isn't correct.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
