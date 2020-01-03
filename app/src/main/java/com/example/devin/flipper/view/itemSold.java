package com.example.devin.flipper.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class itemSold extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "itemSold";

    DatabaseHelper mDatabaseHelper;
    private EditText priceSold;
    private TextView dateSold, itemName;
    private Button btnAddSoldItem;
    private int selectedItemID;
    private String selectedItemName;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_sold);

        mDatabaseHelper = new DatabaseHelper(this );

        priceSold = (EditText) findViewById(R.id.priceSold);
        dateSold = (TextView) findViewById(R.id.dateSold);
        itemName = (TextView) findViewById(R.id.itemName);
        btnAddSoldItem = (Button) findViewById(R.id.btnAddSoldItem);

        Intent recievedIntent = getIntent();
        selectedItemID = recievedIntent.getIntExtra("itemId", -1 );
        selectedItemName = recievedIntent.getStringExtra("itemName");

        final int itemId = selectedItemID;

        itemName.setText(selectedItemName);

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        final String currentDate = sdf.format(new Date());
        //   Date currentTime = Calendar.getInstance().getTime();
        dateSold.setText(currentDate);

        btnAddSoldItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Cursor data = mDatabaseHelper.getPricePurchased(selectedItemID);
                String pricePurchasedStr = null;
                while ( data.moveToNext() ) {
                    pricePurchasedStr = data.getString(0);
                }

                final double pricePurchased = Double.valueOf(pricePurchasedStr); //Need to pass in this variable eventually...

                Intent receivedIntent = getIntent();

                String priceSoldText = priceSold.getText().toString();
                double priceSoldVal = Double.valueOf(priceSoldText);
                double priceProfit = priceSoldVal -  pricePurchased;

                if(priceSoldVal != 0) {
                    updateForPurchase(itemId, priceSoldVal, priceProfit, currentDate);
                } else {
                    toastMessage( "Please put something in the textbox!" );
                }

            }
        });

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    public void updateForPurchase( int itemId, double priceSoldVal, double priceProfit, String currentDate ) {

        try {
            mDatabaseHelper.updateIsSold(itemId);
            mDatabaseHelper.updateDateSold(itemId, currentDate);
            mDatabaseHelper.updatePriceProfit(itemId, priceProfit);
            mDatabaseHelper.updatePriceSold(itemId, priceSoldVal);

            Intent intent = new Intent(itemSold.this, alreadySold.class );
            //           intent.putExtra("ItemId", itemID );
            startActivity( intent );

        } catch (Exception e) {
            throw e;
        }
    }

    private void toastMessage( String message ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_screen:
                Intent intent = new Intent(itemSold.this, HomeScreen.class);
                startActivity(intent);
                break;
            case R.id.nav_inventory:
                intent = new Intent(itemSold.this, currentInventory.class);
                startActivity(intent);
                break;
            case R.id.nav_already_sold:
                intent = new Intent(itemSold.this, alreadySold.class);
                startActivity(intent);
                break;
            case R.id.nav_all_items:
                intent = new Intent(itemSold.this, allItemsList.class);
                startActivity(intent);
                break;
            case R.id.nav_list_item:
                intent = new Intent(itemSold.this, itemAdd.class);
                startActivity(intent);
                break;
            case R.id.nav_item_sold:
                intent = new Intent(itemSold.this, itemSold.class);
                startActivity(intent);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);

        return true;

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
