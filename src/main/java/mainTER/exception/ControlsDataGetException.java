package mainTER.exception;

public class ControlsDataGetException extends Exception {

    private final String message;

    public ControlsDataGetException(String nameCharacter){
        this.message = "Les informations du personnages " + nameCharacter + " n'existent pas !";
    }

    @Override
    public String getMessage(){
        return message;
    }
}
