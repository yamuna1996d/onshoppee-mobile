package com.onshop.yamuna.onshoppee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

//import android.content.DialogInterface;
//import android.graphics.ColorSpace;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.internal.service.Common;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//import com.onshop.yamuna.onshoppee.Database.Database;
import com.onshop.yamuna.onshoppee.Databases.Database;
import com.onshop.yamuna.onshoppee.Models.Order;
import com.onshop.yamuna.onshoppee.Models.Request;
//import com.onshop.yamuna.onshoppee.ViewHolder.CartAdapter;
import com.onshop.yamuna.onshoppee.Prevalent.Prevalent;
import com.onshop.yamuna.onshoppee.ViewHolder.CartViewHolder;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Carts extends AppCompatActivity {
RecyclerView recyclerView;
RecyclerView.LayoutManager layoutManager;
FirebaseDatabase firebaseDatabase;
DatabaseReference requests;
TextView txtotal;
Button orderplace;
List<Order>cart=new ArrayList<>();
CartViewHolder.CartAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carts);

        txtotal=(TextView)findViewById(R.id.totals);
        orderplace=(Button)findViewById(R.id.btnplaceorder);

//        firebaseDatabase=FirebaseDatabase.getInstance();
//        requests=firebaseDatabase.getReference("Requests");
        recyclerView=(RecyclerView)findViewById(R.id.listcart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        orderplace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (cart.size()> 0)
                showAlertDialoge();
                else
                    Toast.makeText(Carts.this,"Your Cart is empty !!!",Toast.LENGTH_SHORT).show();

            }
        });

        loadSpicelist();
    }

    private void loadSpicelist() {

      cart = new Database(this).getCarts();
       adapter = new CartViewHolder.CartAdapter(cart,this);
       adapter.notifyDataSetChanged();
       recyclerView.setAdapter(adapter);

        //Calculate total price
        int totals = 0;
        for (Order order : cart){
            totals += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        Locale locale = new Locale("malayalam", "INDIA");
        NumberFormat fmt = NumberFormat.getCurrencyInstance();
        txtotal.setText(fmt.format(totals));
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle().equals(Prevalent.DELETE))
            deleteCart(item.getOrder());
        return true;
    }

    private void deleteCart(int position) {
        cart.remove(position);
        new Database(this).cleanCart();
        for (Order item:cart)
            new Database(this).addToCart(item);

        loadSpicelist();
    }

    private void showAlertDialoge() {
        AlertDialog.Builder alertDialog=new AlertDialog.Builder(Carts.this);
        alertDialog.setTitle("One more step !");
        alertDialog.setMessage("Enter your Address");
        final EditText edtAddress=new EditText(Carts.this);
        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alertDialog.setView(edtAddress);
        alertDialog.setIcon(R.drawable.nav_cart);
        alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final DatabaseReference requests=FirebaseDatabase.getInstance().getReference().child("Requests");
                Request requestss = new Request(
                        Prevalent.currentonlineUser.getPhone(),
                        Prevalent.currentonlineUser.getName(),
                         edtAddress.getText().toString(),
                         txtotal.getText().toString(),
                        cart
                );
                requests.child(String.valueOf(System.currentTimeMillis()))
                       .setValue(requestss);

                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Carts.this, "Thank you, Order Place.", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

    alertDialog.show();
    }



//    @Override
//    protected void onStart() {
        //final DatabaseReference cartlistRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
//        FirebaseRecyclerOptions<Order>options=new FirebaseRecyclerOptions.Builder<Order>().setQuery(cartlistRef.child("User View")
//        .child("Spice"),Order.class).build();

//        FirebaseRecyclerAdapter<Order, CartViewHolder> adapter=new FirebaseRecyclerAdapter<Order, CartViewHolder>() {
//            @Override
//            protected void onBindViewHolder(@NonNull CartViewHolder cartViewHolder, int i, @NonNull Order order) {
//            cartViewHolder.txtProductName.setText(order.getSpicename());
//            cartViewHolder.txtProductPrice.setText(order.getPrice());
//            }
//
//            @NonNull
//            @Override
//            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//               View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cartlayout,parent,false);
//               CartViewHolder holder=new CartViewHolder(view);
//               return holder;
//            }
//        };
//        recyclerView.setAdapter(adapter);
//        adapter.startListening();

//        super.onStart();
    //}
}
