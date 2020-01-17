package com.continental.accelerometerexperimentation;

import com.continental.accelerometerexperimentation.sensors.FakeRecordingListener;
import com.continental.accelerometerexperimentation.sensors.MissingHardwareException;
import com.continental.accelerometerexperimentation.sensors.Recorder;
import com.continental.accelerometerexperimentation.sensors.RecordingListener;
import com.continental.accelerometerexperimentation.sensors.StubRecorder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DataRecorderTest {

    private Recorder recorder ;
    private RecordingListener fakeRecordingListener ;

    @Before
    public void setUp(){
        recorder = new StubRecorder() ;
        fakeRecordingListener = new FakeRecordingListener();
    }

    @Test
    @Ignore
    public void testShouldFailToRecordWhenSensorIsUnavailable(){
        try {
            recorder.record();
            Assert.fail("Exception not thrown ");
        }catch (MissingHardwareException exception){

        }
    }

    @Test
    public void testShouldBeRecordingWhenRecordingIsStarted() throws MissingHardwareException {
        recorder.record();
        Assert.assertTrue(recorder.isRecording());
    }

    @Test
    public void testSHouldNotBeRecordingWhenRecordingIsStopped(){
        recorder.stopRecording();
        Assert.assertFalse(recorder.isRecording());


    }

}
