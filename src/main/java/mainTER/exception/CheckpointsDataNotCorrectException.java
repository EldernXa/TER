package mainTER.exception;

public class CheckpointsDataNotCorrectException extends Exception{

    private final String message;

    public CheckpointsDataNotCorrectException(){
        this.message = "Les données fournis pour ce checkpoints ne sont pas correct.";
    }

    @Override
    public String getMessage(){
        return message;
    }

}
