package com.continental.accelerometerexperimentation.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.continental.accelerometerexperimentation.R;
import com.continental.accelerometerexperimentation.persistence.PersistenceGateway;

import java.util.Set;

public class HistoricListAdapter extends RecyclerView.Adapter {
    private final Context context;
    private final PersistenceGateway persistenceGateway;
    private final String configuration;

    public HistoricListAdapter(Context context, PersistenceGateway persistenceGateway,String configuration) {
        this.context = context ;
        this.persistenceGateway = persistenceGateway ;
        this.configuration = configuration ;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.historic_list_item,parent,false) ;
        return new HistoricItemViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        HistoricItemViewHolder itemViewHolder = (HistoricItemViewHolder) holder;
        Set<String> approachesDone = persistenceGateway.valuesFromKey(configuration) ;
        String approach = context.getResources().getStringArray(R.array.approachs)[position] ;
        itemViewHolder.setValues(approachesDone,approach);
    }

    @Override
    public int getItemCount() {
        return context.getResources().getStringArray(R.array.approachs).length;
    }
}
