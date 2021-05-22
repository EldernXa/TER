package mainTER.exception;

/**
 * Exception for when the data for the checkpoint already exist.
 */
public class CheckpointsDataAlreadyExistException extends Exception{

    private final String message;

    public CheckpointsDataAlreadyExistException(){
        this.message = "Les données de checkpoints existe déjà.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
