package com.example.devin.flipper.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.devin.flipper.R;

public class baseAssets extends AppCompatActivity {

    TextView baseAssets, liquidAssets, ownedItemAssets;
    Button updateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_assets);

        baseAssets = (TextView) findViewById(R.id.baseAssets);
        liquidAssets = (TextView) findViewById(R.id.liquidAssets);
        ownedItemAssets = (TextView) findViewById(R.id.ownedItemAssets);
        updateButton = (Button) findViewById(R.id.updateButton);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

    }
}
