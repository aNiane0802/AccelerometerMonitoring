package com.continental.accelerometerexperimentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.continental.accelerometerexperimentation.CentralExperience.Experience;
import com.continental.accelerometerexperimentation.R;
import com.continental.accelerometerexperimentation.persistence.FilePersistenceGateway;
import com.continental.accelerometerexperimentation.persistence.PersistenceGateway;
import com.continental.accelerometerexperimentation.sensors.AccelerometerDataRecorder;
import com.continental.accelerometerexperimentation.sensors.MissingHardwareException;
import com.continental.accelerometerexperimentation.sensors.Recorder;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RecordingActivity extends AppCompatActivity {

    private Recorder recorder ;
    private PersistenceGateway persistenceGateway ;
    private TextView timeTrackingTextView ;
    private ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1) ;
    private float elapsedTime = 0.0f ;
    private double experienceDuration = 120.00;
    private PowerManager.WakeLock wakeLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);
        setupRecordingAndPersistence();
        setTitle(getString(R.string.recordingActivityTitle));
        setupTimeTrackingTextView() ;
        setupSwitch();
    }

    private void setupSwitch() {
        SwitchMaterial switchMaterial = findViewById(R.id.switchMaterial);
        switchMaterial.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                tearDownAndReleaseResources();
            }
        });
    }

    private void tearDownAndReleaseResources(){
        stopRecording();
        releaseCPULock() ;
        startDataSendingActivity();

    }

    private void stopRecording() {
        recorder.stopRecording();
    }

    private void releaseCPULock() {
        wakeLock.release();
    }

    private void startDataSendingActivity() {
        Intent intent = new Intent(this, DataSendingActivity.class);
        intent.putExtras(getIntent().getExtras()) ;
        Experience.persistenceGateway =  persistenceGateway;
        finish();
        startActivity(intent);
    }

    private void setupTimeTrackingTextView() {
        timeTrackingTextView = findViewById(R.id.timeTrackingTextView) ;
        timeTrackingTextView.setText(getString(R.string.remainingTimeTextView,120.00));
    }

    private void setupRecordingAndPersistence() {
        persistenceGateway = new FilePersistenceGateway(this) ;
        recorder = new AccelerometerDataRecorder(this);
        recorder.addRecordingListener(persistenceGateway);
    }

    @Override
    public void onBackPressed() {
        Log.d("BackButton","Back pressed ") ;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!recorder.isRecording())
            acquireLockAndRecordAccelerometerValues();
    }

    private void acquireLockAndRecordAccelerometerValues() {
        acquireCpuLock();
        recordAccelerometerValues();
    }

    private void acquireCpuLock() {
        PowerManager powerManager = (PowerManager) getSystemService(POWER_SERVICE) ;
        if(powerManager!=null){
            String powerLockTag = "BackgroundProcessing::Scheduler";
            wakeLock = powerManager.newWakeLock(PowerManager.PARTIAL_WAKE_LOCK, powerLockTag);
            wakeLock.acquire();
        }
    }

    private void recordAccelerometerValues() {
        try {
            recorder.record();
            resumeTimer();
        } catch (MissingHardwareException e) {
            e.printStackTrace();
        }
    }

    private void resumeTimer() {
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                elapsedTime += 0.01 ;
                endExperienceIfNeeded();
                updateUi();
            }
        },0,10, TimeUnit.MILLISECONDS) ;
    }

    private void endExperienceIfNeeded() {
        if(experienceDuration-elapsedTime<10e-3){
            scheduler.shutdown();
            tearDownAndReleaseResources();
        }
    }

    private void updateUi() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeTrackingTextView.setText(getString(R.string.remainingTimeTextView, experienceDuration -elapsedTime));
            }
        });
    }
}
