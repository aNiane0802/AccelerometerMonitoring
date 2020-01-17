package com.continental.accelerometerexperimentation.sensors;

public interface Recorder {
    void record() throws MissingHardwareException;
    void addRecordingListener(RecordingListener recordingListener);
    void stopRecording() ;
    boolean isRecording();
}
