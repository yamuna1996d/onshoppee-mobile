package com.onshop.yamuna.onshoppee.ViewHolder;



import android.view.View;
import android.widget.TextView;

import com.onshop.yamuna.onshoppee.Interfacs.ItemClickListener;
import com.onshop.yamuna.onshoppee.R;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class OrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
public TextView txtid,txtstat,txtphone,txtaddress;
private ItemClickListener itemClickListener;

    public OrderViewHolder(@NonNull View itemView) {
        super(itemView);
        txtaddress=(TextView)itemView.findViewById(R.id.orderaddress);
        txtphone=(TextView)itemView.findViewById(R.id.orderphone);
        txtid=(TextView)itemView.findViewById(R.id.orderid);
        txtstat=(TextView)itemView.findViewById(R.id.orderstatus);
        itemView.setOnClickListener(this);
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(android.view.View v) {
         itemClickListener.onClick(v,getAdapterPosition(),false);


    }
}
