package com.agdyaputra.zooms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class FoodViewHolder extends RecyclerView.ViewHolder {

    View mview;

    public FoodViewHolder(View itemview) {
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

    public void setDetails(Context ctx, String foodId, String foodName, String stock){
        TextView mFoodID = mview.findViewById(R.id.foodID);
        TextView mFoodName = mview.findViewById(R.id.foodName);
        TextView mFoodStock = mview.findViewById(R.id.foodStock);


        mFoodID.setText(foodId);
        mFoodName.setText(foodName);
        mFoodStock.setText(stock);
    }

    private FoodViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(FoodViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
    //interface to send callbacks

}
