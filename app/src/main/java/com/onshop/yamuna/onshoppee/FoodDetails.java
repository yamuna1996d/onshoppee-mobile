package com.onshop.yamuna.onshoppee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.onshop.yamuna.onshoppee.Models.Category;
import com.onshop.yamuna.onshoppee.Models.Spices;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;

public class FoodDetails extends AppCompatActivity {
TextView spicenam,spicepric;
ImageView spiceImage;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton btncart;
ElegantNumberButton numberButton;
String spiceName="";
FirebaseDatabase database;
DatabaseReference spices;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        database=FirebaseDatabase.getInstance();
        spices=database.getReference("Spice");
        numberButton=(ElegantNumberButton)findViewById(R.id.numbebutton);
        btncart=(FloatingActionButton)findViewById(R.id.btncart);
        spicenam=(TextView)findViewById(R.id.spicename);
        spicepric=(TextView)findViewById(R.id.spiceprice);
        spiceImage=(ImageView)findViewById(R.id.imgfood);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

        //get data from intent

        if (getIntent()!=null){
            spiceName=getIntent().getStringExtra("SpiceName");

            if (!spiceName.isEmpty()){
                getSpices(spiceName);
            }
        }
    }

    private void getSpices(final String spiceName) {
      spices.child(spiceName).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Spices spi=dataSnapshot.getValue(Spices.class);
              Picasso.with(getBaseContext()).load(spi.getImage()).into(spiceImage);
              collapsingToolbarLayout.setTitle(spi.getName());
              spicepric.setText(spi.getPrice());
              spicenam.setText(spi.getName());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }
}
