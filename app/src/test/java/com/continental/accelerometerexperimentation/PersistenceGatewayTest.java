package com.continental.accelerometerexperimentation;

import android.content.Context;


import com.continental.accelerometerexperimentation.persistence.FilePersistenceGateway;
import com.continental.accelerometerexperimentation.persistence.PersistenceGateway;
import com.continental.accelerometerexperimentation.sensors.FakeRecorder;
import com.continental.accelerometerexperimentation.sensors.MissingHardwareException;
import com.continental.accelerometerexperimentation.sensors.Recorder;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.io.File;
import java.io.IOException;

import androidx.test.core.app.ApplicationProvider;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

@RunWith(RobolectricTestRunner.class)
public class PersistenceGatewayTest {
    private PersistenceGateway persistenceGateway ;
    private Recorder recorder ;
    private Context context = ApplicationProvider.getApplicationContext() ;
    private String name = "test.txt" ;
    private final String key = "key";
    private final String value = "value";

    @Before
    public void setUp() throws MissingHardwareException {
        persistenceGateway = new FilePersistenceGateway(context) ;
        recorder = new FakeRecorder() ;

        recorder.addRecordingListener(persistenceGateway);
        recorder.record();
    }


    @Test
    public void testShouldBeNotifiedWhenRecorderIsActive(){
        assertEquals(1, persistenceGateway.buffer().size());
    }

    @Test
    public void testShouldSaveFileGivenAName(){
        persistenceGateway.createFileWith(name) ;
        File file = new File(context.getFilesDir(),name) ;
        assertTrue(file.exists());
    }

    @Test
    public void testShouldDeleteAnyExistingFileGivenHisName() throws IOException {
        testShouldSaveFileGivenAName();
        persistenceGateway.deleteResourceWith(name) ;
        assertFalse(new File(context.getFilesDir(),name).exists());
    }

    @Test
    public void testShouldWarnErrorGivenAWrongFilenameToDelete(){
        testShouldSaveFileGivenAName();
        String randomName = "randomName";
        try {
            persistenceGateway.deleteResourceWith(randomName);
            fail();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testShouldSaveValueWithKey(){
        persistenceGateway.saveValueWithKey(key, value);
        assertEquals(1,persistenceGateway.valuesFromKey(key).size());
        assertTrue(persistenceGateway.valuesFromKey(key).contains(value));
    }

    @Test
    public void testShouldReturnAValidPathForCreatedFile() throws Exception {
        testShouldSaveFileGivenAName();
        System.out.println(persistenceGateway.pathTo(name));
        assertNotNull(persistenceGateway.pathTo(name));
    }

    @After
    public void tearDown(){
        try {
            persistenceGateway.deleteResourceWith(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
