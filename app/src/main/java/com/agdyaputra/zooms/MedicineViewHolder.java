package com.agdyaputra.zooms;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

public class MedicineViewHolder extends RecyclerView.ViewHolder {

    View mview;

    public MedicineViewHolder(View itemview) {
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

    public void setDetails(Context ctx, String medicineId, String medicineName, String Stock){
        TextView mMedID = mview.findViewById(R.id.medicineID);
        TextView mMedName = mview.findViewById(R.id.medicineName);
        TextView mStock = mview.findViewById(R.id.medicineStock);


        mMedID.setText(medicineId);
        mMedName.setText(medicineName);
        mStock.setText(Stock);
    }

    private MedicineViewHolder.ClickListener mClickListener;

    //interface to send callbacks
    public interface ClickListener{
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public void setOnClickListener(MedicineViewHolder.ClickListener clickListener){
        mClickListener = clickListener;
    }
    //interface to send callbacks

}
