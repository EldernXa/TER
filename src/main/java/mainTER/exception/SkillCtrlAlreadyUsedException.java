package mainTER.exception;

/**
 * Exception for when the key control we want to insert in the table Skill are already used.
 */
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
