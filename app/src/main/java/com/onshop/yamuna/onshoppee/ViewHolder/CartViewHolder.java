package com.onshop.yamuna.onshoppee.ViewHolder;

import android.content.Context;
import android.graphics.Color;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.onshop.yamuna.onshoppee.Interfacs.ItemClickListener;
import com.onshop.yamuna.onshoppee.Models.Order;
import com.onshop.yamuna.onshoppee.Prevalent.Prevalent;
import com.onshop.yamuna.onshoppee.R;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
        View.OnCreateContextMenuListener
{
    public TextView txtProductName, txtProductPrice;
    private ItemClickListener itemClickListner;


    public CartViewHolder(View itemView)
    {
        super(itemView);

        txtProductName = itemView.findViewById(R.id.cartiname);
        txtProductPrice = itemView.findViewById(R.id.cartiprice);

        itemView.setOnCreateContextMenuListener(this);
    }


    @Override
    public void onClick(View v) {
        itemClickListner.onClick(v,getAdapterPosition(),false);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.setHeaderTitle("Select action !!!");
        menu.add(0,0,getAdapterPosition(),Prevalent.DELETE);


    }

    public static class CartAdapter extends RecyclerView.Adapter<CartViewHolder>{

        private List<Order> listData = new ArrayList<>();
        private Context context;

        //Constructor


        public CartAdapter(List<Order> listData, Context context) {
            this.listData = listData;
            this.context = context;
        }

        @NonNull
        @Override
        public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(context);
            View itemView = inflater.inflate(R.layout.cartlayout, parent, false);
            return new CartViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

//            TextDrawable drawable = TextDrawable.builder().
//                    buildRound(""+listData.get(position).getQuantity(), Color.RED);
//            holder.imgcart_count.setImageDrawable(drawable);

            Locale locale = new Locale("en", "US");
            NumberFormat fmt = NumberFormat.getCurrencyInstance();
            int prices = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
            holder.txtProductPrice.setText(fmt.format(prices));

            holder.txtProductName.setText(listData.get(position).getSpicename());
        }

        @Override
        public int getItemCount() {
            return listData.size();
        }
    }

    public void setItemClickListner(ItemClickListener itemClickListner) {
        this.itemClickListner = itemClickListner;
    }
}