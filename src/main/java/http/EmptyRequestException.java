package http;

public class EmptyRequestException extends Exception{

    private static final long serialVersionUID = 1L;

    EmptyRequestException(){
    }

    EmptyRequestException(String message){
        super(message);
    }

    public EmptyRequestException(Throwable cause) {
        super(cause);
    }

    public EmptyRequestException(String message, Throwable cause) {
        super(message, cause);
    }

    protected EmptyRequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
