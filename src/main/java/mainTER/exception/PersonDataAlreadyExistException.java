package mainTER.exception;

/**
 * Exception for when the data we want to insert for the table Character already exist.
 */
public class PersonDataAlreadyExistException extends Exception{

    private final String message;

    public PersonDataAlreadyExistException(String nameCharacter){
        this.message = "The Character " + nameCharacter + " already exist in the database.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
