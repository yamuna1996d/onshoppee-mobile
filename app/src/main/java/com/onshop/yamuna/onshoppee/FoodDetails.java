package com.onshop.yamuna.onshoppee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
//import com.onshop.yamuna.onshoppee.Database.Database;
import com.onshop.yamuna.onshoppee.Databases.Database;
import com.onshop.yamuna.onshoppee.Models.Category;
import com.onshop.yamuna.onshoppee.Models.Order;
import com.onshop.yamuna.onshoppee.Models.Rating;
import com.onshop.yamuna.onshoppee.Models.Spices;
import com.onshop.yamuna.onshoppee.Prevalent.Prevalent;
import com.rey.material.widget.FloatingActionButton;
import com.squareup.picasso.Picasso;
import com.stepstone.apprating.AppRatingDialog;
import com.stepstone.apprating.listener.RatingDialogListener;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;

public class FoodDetails extends AppCompatActivity implements RatingDialogListener {
TextView spicenam,spicepric,spicetotal;
ImageView spiceImage;
CollapsingToolbarLayout collapsingToolbarLayout;
FloatingActionButton btncart,btrate;
ElegantNumberButton numberButton;
RatingBar ratingBar;
String spicename="";
//String num,s2,res;
//int nu,s1,n3;
FirebaseDatabase database;
DatabaseReference spices;
DatabaseReference ratings;
Spices currentSpice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        database=FirebaseDatabase.getInstance();
        spices=database.getReference("Spice");
        ratings=database.getReference("Rating");

        numberButton=(ElegantNumberButton)findViewById(R.id.numbebutton);
        btncart=(FloatingActionButton)findViewById(R.id.btncart);
        btrate=(FloatingActionButton)findViewById(R.id.btnrating);
        ratingBar=(RatingBar)findViewById(R.id.ratingBar);

        spicenam=(TextView)findViewById(R.id.spicename);
        spicepric=(TextView)findViewById(R.id.spiceprice);
//        spicetotal=(TextView)findViewById(R.id.total);
        spiceImage=(ImageView)findViewById(R.id.imgfood);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapse);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.CollapsedAppbar);

//        num=numberButton.getNumber().toString();
//        s2=spicepric.getText().toString();
//        nu=Integer.parseInt(s2);
//        s1=Integer.parseInt(num);
//        n3=s1*nu;
//        res=String.valueOf(n3);
//        spicetotal.setText(res);

        btrate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showRating();
            }
        });

        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToCart(new Order(
                        spicename,currentSpice.getName(),numberButton.getNumber(),
                        currentSpice.getPrice()
                ));
                Toast.makeText(FoodDetails.this,"Added to Cart",Toast.LENGTH_SHORT).show();
            }
        });

//        btncart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addtocar();
//            }
//        });

        //get data from intent

       if (getIntent()!=null){
            spicename=getIntent().getStringExtra("SpiceName");

            if (!spicename.isEmpty()){
                getSpices(spicename);
                getRating(spicename);
            }
}
    }

    private void getRating(String spicename) {
        Query spiceRating=ratings.orderByChild("spiceName").equalTo(spicename);
        spiceRating.addValueEventListener(new ValueEventListener() {
            int count=0,sum=0;
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot postsnapShot:dataSnapshot.getChildren()){
                    Rating item=postsnapShot.getValue(Rating.class);
                    sum+=Integer.parseInt(item.getRateValue());
                    count++;
                }
                if (count !=0){
                    float average=sum/count;
                    ratingBar.setRating(average);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void showRating() {
        new AppRatingDialog.Builder().setPositiveButtonText("Submit")
                .setNegativeButtonText("Cancel").setNoteDescriptions(Arrays.asList("Very Bad","Not Bad","Quite Ok","Very Good","Excellent"))
                .setDefaultRating(1)
                .setTitle("Rate this Spice").setDescription("Please Select stars and give your Feedback")
                .setTitleTextColor(R.color.colorAccent).setDescriptionTextColor(R.color.colorPrimary)
                .setHint("Please write your Comment Here........")
                .setHintTextColor(R.color.colorAccent).setCommentTextColor(R.color.colorPrimary)
                .setCommentBackgroundColor(R.color.colorAccent)
                .setWindowAnimation(R.style.RatinDialogeFadeAnim)
                .create(FoodDetails.this).show();
    }

    private void getSpices(final String spiceName) {
      spices.child(spiceName).addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              currentSpice=dataSnapshot.getValue(Spices.class);
              Picasso.with(getBaseContext()).load(currentSpice.getImage()).into(spiceImage);
              collapsingToolbarLayout.setTitle(currentSpice.getName());
              spicepric.setText(currentSpice.getPrice());
              spicenam.setText(currentSpice.getName());
          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });
    }

    @Override
    public void onNegativeButtonClicked() {

    }

    @Override
    public void onNeutralButtonClicked() {

    }

    @Override
    public void onPositiveButtonClicked(int value, @NotNull String comments) {
        final Rating rating=new Rating(Prevalent.currentonlineUser.getPhone(),
                spicename,String.valueOf(value),comments);

        ratings.child(Prevalent.currentonlineUser.getPhone()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.child(Prevalent.currentonlineUser.getPhone()).exists()){
                    ratings.child(Prevalent.currentonlineUser.getPhone()).removeValue();
                    ratings.child(Prevalent.currentonlineUser.getPhone()).setValue(rating);
                }
                else {
                    ratings.child(Prevalent.currentonlineUser.getPhone()).setValue(rating);
                }
                Toast.makeText(FoodDetails.this,"Thank u for the Feedback !!!",Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

//    private void addtocar() {
////        String saveCurrentTime,saveCurrentDate;
////        Calendar calendar=Calendar.getInstance();
////        SimpleDateFormat currentDate=new SimpleDateFormat("MMM dd,yyy");
////        saveCurrentDate=currentDate.format(calendar.getTime());
////
////        SimpleDateFormat currentTime=new SimpleDateFormat("HH:mm:ss a");
////        saveCurrentTime=currentDate.format(calendar.getTime());
//
//       final DatabaseReference cartListRef=FirebaseDatabase.getInstance().getReference().child("Cart List");
//        final HashMap<String,Object>cartMap=new HashMap<>();
//        cartMap.put("SpiceId",spiceName);
//       cartMap.put("Spice Name",spicenam.getText().toString());
//        cartMap.put("Spice Price",spicepric.getText().toString());
//        cartMap.put("Quantity",numberButton.getNumber());
//        cartListRef.child("User view").child("Spice").child(spiceName)
//                .updateChildren(cartMap)
//                .addOnCompleteListener(new OnCompleteListener<Void>() {
//                    @Override
//                    public void onComplete(@NonNull Task<Void> task) {
//                       if (task.isSuccessful()){
//                           Toast.makeText(FoodDetails.this,"Added to Cart",Toast.LENGTH_SHORT).show();
//                       }
//                    }
//                });
//
//    }
}
