package mainTER.exception;

/**
 * Exception for when the data requested for the table Skill doesn't exist.
 */
public class SkillDataGetException extends Exception{

    private final String message;

    public SkillDataGetException(String nameCharacter, int numSkill){
        this.message = "Problème dans la récupération des données de compétences " + numSkill +" de " + nameCharacter + ".";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
