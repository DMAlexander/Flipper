package com.example.devin.flipper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.devin.flipper.view.allItemsList;
import com.example.devin.flipper.view.alreadySold;
import com.example.devin.flipper.view.currentInventory;
import com.example.devin.flipper.view.itemAdd;

public class MainActivity extends AppCompatActivity {

    private Button currentInv, alreadySoldItems, runningDates, listItem, allItems;
    private TextView textView, tView1, tView2, tView3, tView4;
    private ImageView iView1, iView2, iView3, iView4;
    private CardView cView1, cView2, cView3, cView4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = (TextView) findViewById(R.id.textView);
        tView1 = (TextView) findViewById(R.id.tView1);
        tView2 = (TextView) findViewById(R.id.tView2);
        tView3 = (TextView) findViewById(R.id.tView3);
        tView4 = (TextView) findViewById(R.id.tView4);
        iView1 = (ImageView) findViewById(R.id.iView1);
        iView2 = (ImageView) findViewById(R.id.iView2);
        iView3 = (ImageView) findViewById(R.id.iView3);
        iView4 = (ImageView) findViewById(R.id.iView4);
        cView1 = (CardView) findViewById(R.id.cView1);
        cView2 = (CardView) findViewById(R.id.cView2);
        cView3 = (CardView) findViewById(R.id.cView3);
        cView4 = (CardView) findViewById(R.id.cView4);

 //       currentInv = (Button) findViewById(R.id.currentInv);
 //       alreadySoldItems = (Button) findViewById(R.id.alreadySoldItems);
  //      runningDates = (Button) findViewById(R.id.runningDates);
  //      listItem = (Button) findViewById(R.id.listItem);
  //      allItems = (Button) findViewById(R.id.allItems);

        cView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, currentInventory.class);
                startActivity(intent);
            }
        });

        cView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, alreadySold.class);
                startActivity(intent);
            }
        });

        cView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, itemAdd.class);
                startActivity(intent);
            }
        });

        cView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, allItemsList.class);
                startActivity(intent);
            }
        });

    }
}
