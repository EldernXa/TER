package mainTER.exception;

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
