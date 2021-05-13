package mainTER.exception;

public class BestProfileDataGetException extends Exception{

    private final String message;

    public BestProfileDataGetException(){
        this.message = "Problème dans la récupération des données.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
