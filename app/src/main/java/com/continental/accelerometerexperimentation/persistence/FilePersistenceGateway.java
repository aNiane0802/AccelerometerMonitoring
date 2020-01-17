package com.continental.accelerometerexperimentation.persistence;

import android.content.Context;
import android.content.SharedPreferences;

import com.continental.accelerometerexperimentation.sensors.DataRecord;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FilePersistenceGateway implements PersistenceGateway {
    private List<DataRecord> records = new ArrayList<>();
    private Context context ;
    private String preferenceFileName = "com.continental.accelerometerexperimentation.preferenceFileName" ;

    public FilePersistenceGateway(Context context){
        this.context = context ;
    }

    @Override
    public List<DataRecord> buffer() {
        return records ;
    }

    @Override
    public void createFileWith(String name) {
        sendAllDataToStreamAndClose(name);
    }

    private void sendAllDataToStreamAndClose(String name) {
        FileOutputStream fileOutputStream;
        try {
            fileOutputStream = context.openFileOutput(name, Context.MODE_PRIVATE);
            String format = "x y z t eventTimestamp systemTimestamp\n" ;
            writeTextToOutputStream(fileOutputStream, format);
            writeBufferToStream(fileOutputStream);
            fileOutputStream.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeBufferToStream(FileOutputStream fileOutputStream) throws IOException {
        for(DataRecord dataRecord : records){
            fileOutputStream.write((dataRecord.toString()+"\n").getBytes());
        }
    }

    private void writeTextToOutputStream(FileOutputStream fileOutputStream, String text) throws IOException {
        fileOutputStream.write(text.getBytes());
    }

    @Override
    public void notify(DataRecord dataRecord) {
        records.add(dataRecord) ;
        System.out.println(dataRecord);
    }

    @Override
    public void deleteResourceWith(String name) throws IOException {
        File file = new File(context.getFilesDir(),name) ;
        if (!file.delete())
            throw new IOException("Failed to suppress file") ;
    }

    @Override
    public void saveValueWithKey(String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,Context.MODE_PRIVATE) ;
        Set<String> values = getValuesAndUpdate(key, value, sharedPreferences);
        applyChanges(key, sharedPreferences, values);

    }

    private Set<String> getValuesAndUpdate(String key, String value, SharedPreferences sharedPreferences) {
        Set<String> values = getValues(key, sharedPreferences);
        values.add(value) ;
        return values;
    }

    private Set<String> getValues(String key, SharedPreferences sharedPreferences) {
        Set<String> formerValues = sharedPreferences.getStringSet(key, Collections.<String>emptySet()) ;
        return new HashSet<>(formerValues);
    }

    private void applyChanges(String key, SharedPreferences sharedPreferences, Set<String> values) {
        SharedPreferences.Editor editor = sharedPreferences.edit() ;
        editor.putStringSet(key,values) ;
        editor.apply() ;
    }

    @Override
    public Set<String> valuesFromKey(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(preferenceFileName,Context.MODE_PRIVATE) ;
        return sharedPreferences.getStringSet(key,Collections.<String>emptySet()) ;
    }

    @Override
    public String pathTo(String name) throws Exception {
        File file = new File(context.getFilesDir(),name) ;
        if(!file.exists())
            throw new Exception("No Resource associated ") ;
        return file.getName();
    }
}
