package com.example.devin.flipper.view;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

public class itemAdd extends AppCompatActivity {

    private static final String TAG = "itemAdd";

    DatabaseHelper mDatabaseHelper;
    private EditText itemName, purchasePrice, projValue;
    private TextView datePurchased, projProfit;
    private Button btnAddItem;
    String itemNameText, purchasePriceText, projValueText, datePurchasedText, projProfitText;

    DecimalFormat currency = new DecimalFormat("$###,###.##");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_add);

        itemName = (EditText) findViewById(R.id.itemName);
        purchasePrice = (EditText) findViewById(R.id.purchPrice);
        projValue = (EditText) findViewById(R.id.projValue);

        datePurchased = (TextView) findViewById(R.id.datePurchased);
        projProfit = (TextView) findViewById(R.id.projProfit);

        btnAddItem = (Button) findViewById(R.id.btnAddItem);

        mDatabaseHelper = new DatabaseHelper(this );

        btnAddItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent recievedIntent = getIntent();

                itemNameText = itemName.getText().toString();
                purchasePriceText = purchasePrice.getText().toString();
                projValueText = projValue.getText().toString();
                datePurchasedText = datePurchased.getText().toString();
                projProfitText = projProfit.getText().toString();

                double purchasePrice = Double.valueOf(purchasePriceText);
                double projValue = Double.valueOf(projValueText);
//                double projProfit = Double.valueOf(projProfitText);
//                double projProfitValue = 0.00;

                double projProfitValue = projValue - purchasePrice;

                if(itemNameText.length()!=0 && purchasePriceText != null && projValueText != null) {
                    insertItem(itemNameText, datePurchasedText, purchasePrice, projValue, projProfitValue);
                } else {
                    toastMessage( "Please put something in the textbox!" );
                }
            }
        });
    }

    public void insertItem( String itemNameText, String datePurchasedText, double purchasePrice,
                            double projValue, double projProfit ) {

        String lowerCaseItemName = itemNameText.toLowerCase();

        boolean insertData = mDatabaseHelper.addItemOwnedData( lowerCaseItemName, datePurchasedText, purchasePrice, projProfit, projValue, "N", null );

        if ( insertData ) {
            toastMessage( "Data successfully inserted!" );

            Cursor data = mDatabaseHelper.getItemId( lowerCaseItemName );
            int itemID = -1;
            while ( data.moveToNext() ) {
                itemID = data.getInt(0);
            }
            toastMessage( "The recipieID is: " + itemID );

            Intent intent = new Intent(itemAdd.this, currentInventory.class );
            intent.putExtra("ItemId", itemID );
            startActivity( intent );
        } else {
            toastMessage( "Something went wrong!" );
        }
    }

    private void toastMessage( String message ) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT ).show();
    }

}