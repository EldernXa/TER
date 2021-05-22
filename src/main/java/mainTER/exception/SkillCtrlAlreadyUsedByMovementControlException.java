package mainTER.exception;

/**
 * Exception for when the key control we try to insert in the table Skill are already used by the movement.
 */
public class SkillCtrlAlreadyUsedByMovementControlException extends Exception{

    private final String message;

    public SkillCtrlAlreadyUsedByMovementControlException(String ctrlKey){
        this.message = "La touche " + ctrlKey + " est déjà utilisée par une touche de contrôle du personnage.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
