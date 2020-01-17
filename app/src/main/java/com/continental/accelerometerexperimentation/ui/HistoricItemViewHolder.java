package com.continental.accelerometerexperimentation.ui;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.continental.accelerometerexperimentation.R;

import java.util.Set;

class HistoricItemViewHolder extends RecyclerView.ViewHolder {

    private View historicItemView;

    HistoricItemViewHolder(@NonNull View itemView) {
        super(itemView);
        historicItemView = itemView ;
    }

    void setValues(Set<String> approachesDone, String approach){
        updateTextView(approach);
        updateImageView(approachesDone, approach);

    }

    private void updateImageView(Set<String> approachesDone, String approach) {
        ImageView statusImageView = historicItemView.findViewById(R.id.statusImageView);
        if(approachesDone.contains(approach))
            statusImageView.setImageResource(R.drawable.rounded_done_icone);
        else
            statusImageView.setImageResource(R.drawable.rounded_undone_icone);
    }

    private void updateTextView(String approach) {
        TextView approachTextView = historicItemView.findViewById(R.id.approachTextView) ;
        approachTextView.setText(approach);
    }
}
