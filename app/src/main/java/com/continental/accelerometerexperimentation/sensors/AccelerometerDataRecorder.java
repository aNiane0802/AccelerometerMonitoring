package com.continental.accelerometerexperimentation.sensors;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.ArrayList;
import java.util.List;

public class AccelerometerDataRecorder implements Recorder , SensorEventListener {

    private final String message = "Accelerometer is not available on this device.";
    private final double nanoSecondsExposant = 1e9;
    private final double millisExposant = 1e3;
    private SensorManager sensorManager ;
    private Context context ;
    private List<RecordingListener> listeners = new ArrayList<>();
    private boolean isFirstEvent= true;
    private long lastTimestamp;
    private double t ;
    private boolean isRecording ;

    public AccelerometerDataRecorder(Context context) {
        this.context = context;
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
    }

    @Override
    public void record() throws MissingHardwareException {
        checkIfHardwareAvailability();
        isRecording = true ;
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) ;
        int samplingPeriod = 10000;
        sensorManager.registerListener(this,sensor,samplingPeriod) ;

    }

    private void checkIfHardwareAvailability() throws MissingHardwareException {
        if(sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION)==null)
            throw new MissingHardwareException(message) ;
    }

    @Override
    public void addRecordingListener(RecordingListener recordingListener) {
        listeners.add(recordingListener) ;
    }

    @Override
    public void stopRecording() {
        isRecording = false ;
        sensorManager.unregisterListener(this);
        removeListeners() ;
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }

    private void removeListeners() {
        listeners.clear();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float x = event.values[0] ;
        float y = event.values[1] ;
        float z = event.values[2] ;
        setupTemporalComponent(event);
        notifyListeners(new DataRecord(x,y,z,t,event.timestamp/ nanoSecondsExposant,System.currentTimeMillis()/ millisExposant)) ;
    }


    private void setupTemporalComponent(SensorEvent event) {
        if(isFirstEvent){
            lastTimestamp = event.timestamp ;
            isFirstEvent = false ;
        }
        t += (event.timestamp-lastTimestamp)/ nanoSecondsExposant;
        lastTimestamp = event.timestamp ;
    }


    private void notifyListeners(final DataRecord dataRecord) {
        for(RecordingListener listener : listeners)
            listener.notify(dataRecord);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println(accuracy);
    }
}
