package com.agdyaputra.zooms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class PlaceViewHolder extends RecyclerView.ViewHolder {

    View mview;

    public PlaceViewHolder(View itemview) {
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

    public void setDetails(Context ctx, String placeId, String placeName, String habitat){
        TextView mPlaceID = mview.findViewById(R.id.placeID);
        TextView mPlaceName = mview.findViewById(R.id.placeName);
        TextView mHabitat = mview.findViewById(R.id.placeHabitat);


        mPlaceID.setText(placeId);
        mPlaceName.setText(placeName);
        mHabitat.setText(habitat);
    }

    private PlaceViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(PlaceViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
    //interface to send callbacks


}
