package com.onshop.yamuna.onshoppee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class intro extends AppCompatActivity {
    private TextView tv;
    private ImageView im;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        tv=(TextView)findViewById(R.id.te);
        im=(ImageView)findViewById(R.id.img);
        Animation animation=AnimationUtils.loadAnimation(this,R.anim.mytransaction);
        tv.startAnimation(animation);
        im.startAnimation(animation);
        final Intent i=new Intent(getApplicationContext(),onshoppe.class);

        Thread timer=new Thread(){
            public void run(){
                try{
                    sleep(5000);
                }
                catch (InterruptedException i){
                    i.printStackTrace();
                }
                finally {
                    startActivity(i);
                    finish();
                }
            }
        };
        timer.start();

    }
}
