package com.continental.accelerometerexperimentation.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.continental.accelerometerexperimentation.R;

public class ApproachChoiceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private int approachIndex  = 0 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_approach_choice);
        String configuration = getExtraWith(getString(R.string.configurationExtra));
        TextView experienceTextView = findViewById(R.id.experienceTextView);
        experienceTextView.setText(getString(R.string.experience,configuration));
        setupDropDownMenu();
        setTitle(getString(R.string.configurationActivityTitle));

    }

    private String getExtraWith(String key) {
        Intent intent = getIntent() ;
        return intent.getExtras().getString(key) ;
    }

    private void setupDropDownMenu() {
        Spinner dropDownChoiceSpinner = findViewById(R.id.choicesSpinner) ;
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item,
                getStringArray(R.array.approachs));
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        dropDownChoiceSpinner.setAdapter(adapter);
        dropDownChoiceSpinner.setOnItemSelectedListener(this);
    }

    private String[] getStringArray(int resourceId) {
        return getResources().getStringArray(resourceId);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        approachIndex = position ;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    public void startRecordingActivity(View v){
        String configuration = getExtraWith(getString(R.string.configurationExtra)) ;
        String approach = getStringArray(R.array.approachs)[approachIndex] ;
        Intent intent = buildIntent(configuration, approach);
        startActivity(intent);

    }

    private Intent buildIntent(String configuration, String approach) {
        Intent intent = new Intent(this, RecordingActivity.class) ;
        intent.putExtra(getString(R.string.configurationExtra),configuration) ;
        intent.putExtra(getString(R.string.approachExtra),approach) ;
        return intent;
    }
}
