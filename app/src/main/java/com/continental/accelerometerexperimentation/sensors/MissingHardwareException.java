package com.continental.accelerometerexperimentation.sensors;

public class MissingHardwareException extends Exception {
    public MissingHardwareException(String message){
        super(message);
    }
}
