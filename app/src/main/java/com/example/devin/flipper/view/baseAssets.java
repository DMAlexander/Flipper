package com.example.devin.flipper.view;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class baseAssets extends AppCompatActivity {

    EditText baseAssets, liquidAssets, ownedItemAssets;
    Button updateButton;
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_assets);

        baseAssets = (EditText) findViewById(R.id.baseAssets);
        liquidAssets = (EditText) findViewById(R.id.liquidAssets);
        ownedItemAssets = (EditText) findViewById(R.id.ownedItemAssets);
        updateButton = (Button) findViewById(R.id.updateButton);
        mDatabaseHelper = new DatabaseHelper(this);

        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                try {
                    String baseAssetsStr = baseAssets.getText().toString();
                    double baseAssetsVal = Double.parseDouble(baseAssetsStr);
                    String liquidAssetsStr = liquidAssets.getText().toString();
                    double liquidAssetsVal = Double.parseDouble(liquidAssetsStr);
                    String ownedItemAssetsValueStr = ownedItemAssets.getText().toString();
                    double ownedItemAssetsValues = Double.parseDouble(ownedItemAssetsValueStr);
                    double totalAssets = baseAssetsVal + ownedItemAssetsValues + liquidAssetsVal;

                    mDatabaseHelper.updateBaseAssets(baseAssetsVal);
                    mDatabaseHelper.updateLiquidAssets(liquidAssetsVal);
                    mDatabaseHelper.updateOwnedItemAssetsValues(ownedItemAssetsValues);
                    mDatabaseHelper.updateTotalAssets(totalAssets);

                    Intent intent = new Intent(baseAssets.this, HomeScreen.class);
                    startActivity(intent);

                } catch (Exception e) {
                    throw e;
                }
            }
        });

    }
}
