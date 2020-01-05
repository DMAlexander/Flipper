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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.flipper.MainActivity;
import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class itemAdd extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "itemAdd";

    DatabaseHelper mDatabaseHelper;
    private EditText itemName, purchasePrice, projValue;
    private TextView datePurchased, projProfit;
    private Button btnAddItem;
    String itemNameText, purchasePriceText, projValueText, datePurchasedText, projProfitText;
    public DrawerLayout drawer;

    DecimalFormat currency = new DecimalFormat("$###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);
        itemName = (EditText) findViewById(R.id.itemName);
        purchasePrice = (EditText) findViewById(R.id.purchPrice);
        projValue = (EditText) findViewById(R.id.projValue);
        datePurchased = (TextView) findViewById(R.id.datePurchased);
   //     projProfit = (TextView) findViewById(R.id.projProfit);
        btnAddItem = (Button) findViewById(R.id.btnAddItem);
        mDatabaseHelper = new DatabaseHelper(this );

        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
        final String currentDate = sdf.format(new Date());
        //   Date currentTime = Calendar.getInstance().getTime();
        datePurchased.setText(currentDate);

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recievedIntent = getIntent();

                itemNameText = itemName.getText().toString();
                purchasePriceText = purchasePrice.getText().toString();
                projValueText = projValue.getText().toString();

//                datePurchasedText = datePurchased.getText().toString();
//                projProfitText = projProfit.getText().toString();

                double purchasePrice = Double.valueOf(purchasePriceText);
                double projValue = Double.valueOf(projValueText);
//                double projProfit = Double.valueOf(projProfitText);
//                double projProfitValue = 0.00;

                double projProfitValue = projValue - purchasePrice;

                if(itemNameText.length()!=0 && purchasePriceText != null && projValueText != null) {
                    insertItem(itemNameText, currentDate, purchasePrice, projValue, projProfitValue);
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

    public void insertItem( String itemNameText, String currentDate, double purchasePrice,
                            double projValue, double projProfitValue ) {

        String lowerCaseItemName = itemNameText.toLowerCase();

        boolean insertData = mDatabaseHelper.addItemOwnedData( lowerCaseItemName, currentDate, purchasePrice, projValue, projProfitValue );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );
/*
            Cursor data = mDatabaseHelper.getItemId( lowerCaseItemName );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipieID is: " + itemID );
*/
            Intent intent = new Intent(itemAdd.this, currentInventory.class );
 //           intent.putExtra("ItemId", itemID );
            startActivity( intent );
        } else {
            toastMessage( "Something went wrong!" );
        }
    }

    private void toastMessage( String message ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_screen:
                Intent intent = new Intent(itemAdd.this, HomeScreen.class);
                startActivity(intent);
                break;
            case R.id.nav_inventory:
                intent = new Intent(itemAdd.this, currentInventory.class);
                startActivity(intent);
                break;
            case R.id.nav_already_sold:
                intent = new Intent(itemAdd.this, alreadySold.class);
                startActivity(intent);
                break;
            case R.id.nav_all_items:
                intent = new Intent(itemAdd.this, allItemsList.class);
                startActivity(intent);
                break;
            case R.id.nav_list_item:
                intent = new Intent(itemAdd.this, itemAdd.class);
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
