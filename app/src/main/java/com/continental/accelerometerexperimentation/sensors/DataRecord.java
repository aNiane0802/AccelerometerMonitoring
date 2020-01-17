package com.continental.accelerometerexperimentation.sensors;

public class DataRecord {

    private final double t;
    private final double x ;
    private final double y ;
    private final double z ;
    private final double systemTimestamp;
    private final double eventTimestamp;

    DataRecord(double x, double y, double z, double t, double eventTimestamp, double systemTimestamp) {
        this.x = x ;
        this.y = y ;
        this.z = z ;
        this.t = t ;
        this.eventTimestamp = eventTimestamp ;
        this.systemTimestamp = systemTimestamp ;
    }

    @Override
    public String toString() {
        String convertedX = replaceIn(doubleToString(x),'.',',') ;
        String convertedY = replaceIn(doubleToString(y),'.',',') ;
        String convertedZ = replaceIn(doubleToString(z),'.',',') ;
        String convertedT = replaceIn(doubleToString(t),'.',',') ;
        String convertedEventTimestamp = replaceIn(doubleToString(eventTimestamp),'.',',') ;
        String convertedSystemTimestamp = replaceIn(doubleToString(systemTimestamp),'.',',') ;

        return String.format("%s %s %s %s %s %s", convertedX, convertedY, convertedZ, convertedT,convertedEventTimestamp, convertedSystemTimestamp) ;
    }

    private String doubleToString(double value) {
        return String.valueOf(value) ;
    }

    private String replaceIn(String string,char symbol,char otherSymbol ){
        return string.replace(symbol,otherSymbol) ;
    }
}
