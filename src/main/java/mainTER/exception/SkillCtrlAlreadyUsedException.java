package mainTER.exception;

public class SkillCtrlAlreadyUsedException extends Exception{

    private final String message;

    public SkillCtrlAlreadyUsedException(String nameCharacter, String ctrlKey){
        this.message = "The key " + ctrlKey + " for the character " + nameCharacter + " is already used for an another skill.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
