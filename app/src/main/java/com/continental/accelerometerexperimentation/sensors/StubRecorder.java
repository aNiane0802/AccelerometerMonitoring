package com.continental.accelerometerexperimentation.sensors;

public class StubRecorder implements Recorder {

    private boolean isHardwareAvailable = true ;
    private boolean isRecording ;
    @Override
    public void record() throws MissingHardwareException {
        if(!isHardwareAvailable)
            throw new MissingHardwareException("Sensor is not available") ;
        isRecording = true ;
    }

    @Override
    public void addRecordingListener(RecordingListener recordingListener) {

    }

    @Override
    public void stopRecording() {
        isRecording = false ;
    }

    @Override
    public boolean isRecording() {
        return isRecording;
    }
}
