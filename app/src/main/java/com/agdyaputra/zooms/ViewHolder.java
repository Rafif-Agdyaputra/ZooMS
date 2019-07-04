package com.agdyaputra.zooms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ViewHolder extends RecyclerView.ViewHolder {

    View mview;


    // This is ANIMAL VIEWHOLDER //
    public ViewHolder(View itemview){
        super(itemview);

        mview = itemview;

        //button Edit click
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
            }
        });

        //button Edit long click
        itemview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mClickListener.onItemClick(view, getAdapterPosition());
                return true;
            }
        });

    }

    public void setDetails(Context ctx, String animalId, String animalName, String animalSpecies, String animalGender, String animalPlacement, String animalCondition){
        TextView mAnimalID = mview.findViewById(R.id.animalID);
        TextView mAnimalName = mview.findViewById(R.id.animalName);
        TextView mAnimalSpecies = mview.findViewById(R.id.animalSpecies);
        TextView mAnimalGender = mview.findViewById(R.id.animalGender);
        TextView mAnimalPlacement = mview.findViewById(R.id.animalPlacement);
        TextView mAnimalCondition = mview.findViewById(R.id.animalCondition);


        mAnimalID.setText(animalId);
        mAnimalName.setText(animalName);
        mAnimalSpecies.setText(animalSpecies);
        mAnimalGender.setText(animalGender);
        mAnimalCondition.setText(animalCondition);
        mAnimalPlacement.setText(animalPlacement);
    }

    private ViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(ViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
    //interface to send callbacks
}
