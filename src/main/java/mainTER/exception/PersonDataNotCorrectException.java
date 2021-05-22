package mainTER.exception;

/**
 * Exception for when the inserting data for the table Character aren't correct.
 */
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
