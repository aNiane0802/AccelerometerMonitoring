package com.continental.accelerometerexperimentation.ui.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.continental.accelerometerexperimentation.CentralExperience.Experience;
import com.continental.accelerometerexperimentation.R;
import com.continental.accelerometerexperimentation.formatter.TextFormatter;
import com.continental.accelerometerexperimentation.persistence.PersistenceGateway;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static androidx.core.content.FileProvider.getUriForFile;

public class DataSendingActivity extends AppCompatActivity implements TextWatcher {

    private TextInputEditText filenameEditText ;
    private MaterialButton sendEmailButton;
    private PersistenceGateway persistenceGateway ;
    private String experience;
    private String approach;
    private boolean isFileGenerated = false;
    private int MAIL_SENDING_RESULT = 01;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_sending);
        setupFileNameEditText();
        sendEmailButton = findViewById(R.id.sendEmailButton) ;
        persistenceGateway = Experience.persistenceGateway;
        setExperienceAndApproach();
        setTitle(getString(R.string.dataSendingActivityTitle));
    }

    private void setupFileNameEditText() {
        filenameEditText = findViewById(R.id.fileNameEditText) ;
        filenameEditText.addTextChangedListener(this);
    }

    @Override
    public void onBackPressed() {

    }

    public void backHome(View v){
        if(isFileGenerated)
            deleteFileANdNotifyUser();
        persistenceGateway.saveValueWithKey(experience,approach);
        finish();
        Intent intent = new Intent(this, LandingActivity.class) ;
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    private void deleteFileANdNotifyUser() {
        try {
            persistenceGateway.deleteResourceWith(generatedFileName());
            buildToast(R.string.deletionSuccess).show();
            isFileGenerated = false ;
        } catch (IOException e) {
            e.printStackTrace();
            buildToast(R.string.deletionFailure).show();
        }
    }

    public void sendEmail(View v){
        createFileWithUserInput();
        Uri uri = getUri();
        System.out.println(uri.getPath());
        Intent intent = buildEmailIntent(uri);
        startActivityForResult(Intent.createChooser(intent, getString(R.string.sendWith)),MAIL_SENDING_RESULT);
    }

    private void createFileWithUserInput() {
        persistenceGateway.createFileWith(generatedFileName());
        isFileGenerated = true ;
    }

    private String generatedFileName() {
        String separator = "-";
        String extension = ".txt";
        return TextFormatter.formatWith(new String[]{userInputToFilename(), experience, approach}, separator, extension) ;
    }

    private void setExperienceAndApproach() {
        Bundle extras = getIntent().getExtras();
        experience = extras.getString(getString(R.string.configurationExtra));
        approach = extras.getString(getString(R.string.approachExtra));
    }

    private Intent buildEmailIntent(Uri uri) {
        String emailRecipient = getString(R.string.emailRecipient);
        Intent intent = new Intent(Intent.ACTION_SEND) ;
        intent.setType("message/rfc822") ;
        intent.putExtra(Intent.EXTRA_EMAIL,new String[]{emailRecipient}) ;
        intent.putExtra(Intent.EXTRA_SUBJECT,getString(R.string.mailSubject)) ;
        intent.putExtra(Intent.EXTRA_STREAM,uri) ;
        intent.putExtra(Intent.EXTRA_TEXT,getString(R.string.mailBody,TextFormatter.currentDate()));
        grantPermissionsFor(intent,uri) ;
        return intent;
    }

    private void grantPermissionsFor(Intent intent, Uri uri) {
        List<ResolveInfo> resInfoList = getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION
                    | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
    }

    private Uri getUri() {
        File filePath = new File(getFilesDir(),generatedFileName());
        return getUriForFile(this,"com.continental.accelerometerexperimentation.fileprovider",filePath) ;
    }

    private String userInputToFilename() {
        return filenameEditText.getText().toString();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

        if(filenameEditText.getText().toString().replaceAll("\\s+","").length()>0){
            sendEmailButton.setClickable(true);
            sendEmailButton.setAlpha(1.0f);
        }else{
            sendEmailButton.setClickable(false);
            sendEmailButton.setAlpha(0.3f);
      }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }


    private Toast buildToast(int p) {
        Toast toast = Toast.makeText(this, p, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.BOTTOM|Gravity.CENTER, 0, 0);
        return toast ;
    }

}
