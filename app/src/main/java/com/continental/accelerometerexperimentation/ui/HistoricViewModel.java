package com.continental.accelerometerexperimentation.ui;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.continental.accelerometerexperimentation.persistence.PersistenceGateway;

import java.util.Collections;
import java.util.Set;

public class HistoricViewModel extends ViewModel {
    private MutableLiveData<Set<String>> approachesDone ;
    private PersistenceGateway persistenceGateway ;

    public HistoricViewModel(PersistenceGateway persistenceGateway) {
        this.persistenceGateway = persistenceGateway;
        approachesDone.setValue(Collections.<String>emptySet());
    }

    public LiveData<Set<String>> getApproachesDone(String mode) {
        approachesDone.setValue(persistenceGateway.valuesFromKey(mode));
        return approachesDone ;
    }
}
