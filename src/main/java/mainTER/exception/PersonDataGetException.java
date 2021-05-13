package mainTER.exception;

public class PersonDataGetException extends Exception{

    private final String message;

    public PersonDataGetException(String nameCharacter){
        this.message = "Les informations du personnages " + nameCharacter + " n'existent pas !";
    }

    public PersonDataGetException(){
        this.message = "Aucune données de personnage présente dans la base de données.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
