package mainTER.exception;

public class UpgradeSkillDataGetException extends Exception{

    private final String message;

    public UpgradeSkillDataGetException(){
        message = "Problème dans la récupération des données de la table UpgradeSkill.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
