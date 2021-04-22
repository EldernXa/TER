package mainTER.exception;

public class PersonDataGetException extends Exception{

    private final String message;

    public PersonDataGetException(String nameCharacter){
        this.message = "Les informations du personnages " + nameCharacter + " n'existent pas !";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
