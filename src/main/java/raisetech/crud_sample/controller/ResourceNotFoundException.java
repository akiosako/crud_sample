package raisetech.crud_sample.controller;

public class ResourceNotFoundException extends RuntimeException{
    public ResourceNotFoundException(){
        super();
    }

    public ResourceNotFoundException(String message, Throwable cause){
        super(message,cause);
    }

    public ResourceNotFoundException(String message){
        super(message);
    }

    public ResourceNotFoundException(Throwable cause){
        super(cause);
    }
}



