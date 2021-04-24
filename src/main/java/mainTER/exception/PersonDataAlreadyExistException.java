package mainTER.exception;

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
