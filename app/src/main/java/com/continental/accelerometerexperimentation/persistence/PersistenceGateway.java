package com.continental.accelerometerexperimentation.persistence;

import com.continental.accelerometerexperimentation.sensors.DataRecord;
import com.continental.accelerometerexperimentation.sensors.RecordingListener;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public interface PersistenceGateway extends RecordingListener {
    List<DataRecord> buffer();
    void createFileWith(String name);
    void deleteResourceWith(String name) throws IOException;
    void saveValueWithKey(String key, String value);
    Set<String> valuesFromKey(String key);
    String pathTo(String name) throws Exception;
}
