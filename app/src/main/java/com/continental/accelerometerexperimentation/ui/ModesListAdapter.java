package com.continental.accelerometerexperimentation.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.continental.accelerometerexperimentation.R;

public class ModesListAdapter extends RecyclerView.Adapter {

    private final Context context;
    private String[] configurations ;

    public ModesListAdapter(String[] configurations,Context context){
        this.configurations = configurations ;
        this.context = context ;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.mode_card_view,viewGroup,false) ;
       return new ModeViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ModeViewHolder modeViewHolder = (ModeViewHolder)viewHolder ;
        modeViewHolder.setMode(configurations[i],context);
    }

    @Override
    public int getItemCount() {
        return configurations.length;
    }
}
