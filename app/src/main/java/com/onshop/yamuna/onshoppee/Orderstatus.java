package com.onshop.yamuna.onshoppee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onshop.yamuna.onshoppee.Models.Order;
import com.onshop.yamuna.onshoppee.Models.Request;
import com.onshop.yamuna.onshoppee.Prevalent.Prevalent;
import com.onshop.yamuna.onshoppee.ViewHolder.OrderViewHolder;

public class Orderstatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;
    FirebaseDatabase database;
    DatabaseReference requests;
    FirebaseRecyclerAdapter<Request, OrderViewHolder>adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderstatus);

        database=FirebaseDatabase.getInstance();
        requests=database.getReference("Requests");
        recyclerView=(RecyclerView)findViewById(R.id.listorder);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        loadOrder(Prevalent.currentonlineUser.getPhone());

    }

    private void loadOrder( String phone) {
        Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
//         DatabaseReference requests=FirebaseDatabase.getInstance().getReference().child("Requests");
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.orderlayout,
                OrderViewHolder.class,
                requests.orderByChild("phone").equalTo(phone)
        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {
                viewHolder.txtid.setText(adapter.getRef(position).getKey());
                viewHolder.txtstat.setText(Prevalent.convertCodeToStatus(model.getStatus()));
                viewHolder.txtaddress.setText(model.getAddress());
                viewHolder.txtphone.setText(model.getPhone());

            }
        };
        Toast.makeText(this, "Value: "+phone, Toast.LENGTH_SHORT).show();
        recyclerView.setAdapter(adapter);
    }



}
