package com.onshop.yamuna.onshoppee;

import android.content.Intent;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;

//import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.onshop.yamuna.onshoppee.Interfacs.ItemClickListener;
import com.onshop.yamuna.onshoppee.Models.Category;
import com.onshop.yamuna.onshoppee.Models.Order;
import com.onshop.yamuna.onshoppee.ViewHolder.MenuViewHolder;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class Homeview extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
private DatabaseReference categor;
private RecyclerView recyclerView;
FirebaseDatabase database;

RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeview);
        recyclerView=findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //init firebase

        database=FirebaseDatabase.getInstance();
        categor=database.getReference("Category");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


         loadMenu();
    }




//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        final FirebaseRecyclerOptions<Category> options=new FirebaseRecyclerOptions
//                .Builder<Category>().setQuery(categor,Category.class).build();
//        FirebaseRecyclerAdapter<Category,MenuViewHolder>adapter=new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
//            @Override
//            protected void onBindViewHolder(@NonNull MenuViewHolder menuViewHolder, int i, @NonNull final Category category) {
//                menuViewHolder.textmessage.setText(category.getName());
//
////                Picasso.with(getBaseContext()).load(category.getImage())
////                        .into(menuViewHolder.imageview);
//                final Category clickitem=category;
//                menuViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        Toast.makeText(Homeview.this,""+clickitem.getName(),Toast.LENGTH_SHORT).show();
//                        Intent foodDetails=new Intent(Homeview.this,FoodDetails.class);
//                        foodDetails.putExtra("SpiceName",category.getName());
//                        startActivity(foodDetails);
//                    }
//                });
//            }

//            @NonNull
//            @Override
//            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//                return null;
//            }
//
//        };
//        recyclerView.setAdapter(adapter);
//    }
private void loadMenu() {
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter =
            new FirebaseRecyclerAdapter<Category, MenuViewHolder>(Category.class, R.layout.card, MenuViewHolder.class, categor) {

                @Override
                protected void populateViewHolder(MenuViewHolder menuViewHolder, Category category, int i) {
                    menuViewHolder.textmessage.setText(category.getName());
                    Picasso.with(getBaseContext()).load(category.getImage())
                            .into(menuViewHolder.imageview);
                    final Category clickitem = category;
                    menuViewHolder.setItemClickListener(new ItemClickListener() {
                        @Override
                        public void onClick(View view, int position, boolean isLongClick) {
                            Toast.makeText(Homeview.this, "" + clickitem.getName(), Toast.LENGTH_SHORT).show();
                            Intent foodDetails = new Intent(Homeview.this, FoodDetails.class);
                            foodDetails.putExtra("SpiceName", getRef(position).getKey());
                            startActivity(foodDetails);
                        }
                    });

                }

            };
    recyclerView.setAdapter(adapter);
}





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.homeview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Intent cartIntent=new Intent(Homeview.this,Carts.class);
            startActivity(cartIntent);

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
