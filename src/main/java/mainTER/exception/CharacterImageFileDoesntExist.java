package mainTER.exception;

public class CharacterImageFileDoesntExist extends Exception{

    private final String message;

    public CharacterImageFileDoesntExist(String nameCharacter){
        this.message = "The path for the character " + nameCharacter + " doesn't exist.";
    }

    @Override
    public String getMessage(){
        return message;
    }





}
