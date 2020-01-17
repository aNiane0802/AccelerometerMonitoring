package com.continental.accelerometerexperimentation.ui.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.continental.accelerometerexperimentation.R;
import com.continental.accelerometerexperimentation.ui.ModesListAdapter;

public class LandingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing) ;
        setupModesList() ;
        setTitle(getString(R.string.experimentation));
    }

    private void setupModesList() {
        RecyclerView modesList = findViewById(R.id.modesList) ;
        modesList.setHasFixedSize(true);
        modesList.setLayoutManager(new LinearLayoutManager(this));
        String[] configurations = getResources().getStringArray(R.array.configurations) ;
        modesList.setAdapter(new ModesListAdapter(configurations,this));
    }
}
