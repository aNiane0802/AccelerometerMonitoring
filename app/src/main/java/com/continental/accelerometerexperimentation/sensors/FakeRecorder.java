package com.continental.accelerometerexperimentation.sensors;

import java.util.ArrayList;
import java.util.List;

public class FakeRecorder implements Recorder {
    private List<RecordingListener> recordingListeners = new ArrayList<>();


    @Override
    public void record() throws MissingHardwareException {
        DataRecord dataRecord = new DataRecord(0.1,0.8,0.5, 0.0, 1245, System.nanoTime() / 10e9) ;
        notifyListeners(dataRecord) ;
    }

    private void notifyListeners(DataRecord dataRecord) {
        for (RecordingListener recordingListener : recordingListeners)
            recordingListener.notify(dataRecord);
    }

    @Override
    public void addRecordingListener(RecordingListener recordingListener) {
        recordingListeners.add(recordingListener) ;
    }

    @Override
    public void stopRecording() {

    }

    @Override
    public boolean isRecording() {
        return false;
    }


}
