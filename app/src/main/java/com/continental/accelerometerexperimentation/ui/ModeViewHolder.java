package com.continental.accelerometerexperimentation.ui;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.continental.accelerometerexperimentation.R;
import com.continental.accelerometerexperimentation.persistence.FilePersistenceGateway;
import com.continental.accelerometerexperimentation.ui.activities.ApproachChoiceActivity;

class ModeViewHolder extends RecyclerView.ViewHolder {

    private final String extraName = "Configuration";
    private View modeCardView;
    private Button startButton ;

    ModeViewHolder(@NonNull View itemView) {
        super(itemView);
        modeCardView = itemView ;
    }

    void setMode(String configuration, Context context){
        setupParentViewHolder(configuration,context);
        setupChildRecyclerView(configuration,context) ;
    }

    private void setupChildRecyclerView(String configuration, Context context) {
        RecyclerView recyclerView = modeCardView.findViewById(R.id.historicList);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new HistoricListAdapter(context,new FilePersistenceGateway(context),configuration));
    }

    private void setupParentViewHolder(String configuration, Context context) {
        CardView cardView = modeCardView.findViewById(R.id.modeCardView) ;
        TextView modeButton = cardView.findViewById(R.id.modeTitle) ;
        setupStartButton(cardView,configuration,context);
        modeButton.setText(configuration);
    }

    private void setupStartButton(CardView cardView, final String configuration, Context context) {
        startButton = cardView.findViewById(R.id.startButton) ;
        setTargetAction(configuration,context);
    }

    private void setTargetAction(final String configuration, final Context context) {
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApproachChoiceActivity.class);
                intent.putExtra(extraName,configuration) ;
                context.startActivity(intent);
            }
        });
    }


}
