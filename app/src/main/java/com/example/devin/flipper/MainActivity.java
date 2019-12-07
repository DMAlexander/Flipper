package com.example.devin.flipper;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.devin.flipper.view.allItemsList;
import com.example.devin.flipper.view.alreadySold;
import com.example.devin.flipper.view.currentInventory;
import com.example.devin.flipper.view.itemAdd;

public class MainActivity extends AppCompatActivity {

    private Button currentInv, alreadySoldItems, runningDates, listItem, allItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        currentInv = (Button) findViewById(R.id.currentInv);
        alreadySoldItems = (Button) findViewById(R.id.alreadySoldItems);
  //      runningDates = (Button) findViewById(R.id.runningDates);
        listItem = (Button) findViewById(R.id.listItem);
        allItems = (Button) findViewById(R.id.allItems);

        currentInv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, currentInventory.class);
                startActivity(intent);
            }
        });

        alreadySoldItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, alreadySold.class);
                startActivity(intent);
            }
        });

        listItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, itemAdd.class);
                startActivity(intent);
            }
        });

        allItems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, allItemsList.class);
                startActivity(intent);
            }
        });

    }
}
