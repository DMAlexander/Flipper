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
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class HomeScreen extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "allItemsList";

    private DrawerLayout drawer;
//    private EditText itemName, purchasePrice, projValue;
//    private TextView datePurchased, projProfit;
    private TextView totalAssets, baseAssets, liquidAssets, totalAssetsLabel, baseAssetsLabel, liquidAssetsLabel;
    private Button btnAddItem;
    DatabaseHelper mDatabaseHelper;
    RecyclerView recyclerView;
    private HomeScreenAdapter mAdapter;
    private List<String> listItem;
    ArrayList list1 = new ArrayList();
    ArrayList list2 = new ArrayList();
    ArrayList list3 = new ArrayList();
    ArrayList list4 = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        mDatabaseHelper = new DatabaseHelper(this);

      //  addBaseAssetData(

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    //    recyclerView = findViewById(R.id.recyclerView);
    //    recyclerView.setLayoutManager(new LinearLayoutManager(this));

        /*
        Cursor data4 = mDatabaseHelper.getItemsOwned();
        String itemId = null;
        while (data4.moveToNext()) {
            itemId = data4.getString(0);
        }
        if(itemId != null) {
            mAdapter = new HomeScreenAdapter(this, getAllItems());
        } else {
        }
        recyclerView.setAdapter(mAdapter);
        */

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeScreenAdapter(this, getAllItems());
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new HomeScreenAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position, String recipeName, String recipeId) {

            }
        });

        totalAssets = findViewById(R.id.totalAssets);
        baseAssets = findViewById(R.id.baseAssets);
        liquidAssets = findViewById(R.id.liquidAssets);
        totalAssetsLabel = findViewById(R.id.totalAssetsLabel);
        baseAssetsLabel = findViewById(R.id.baseAssetsLabel);
        liquidAssetsLabel = findViewById(R.id.liquidAssetsLabel);

        /*
        Cursor data = mDatabaseHelper.getItemId( lowerCaseItemName );
        int itemID = -1;
        while ( data.moveToNext() ) {
            itemID = data.getInt(0);
        }
        */

        Cursor data3 = mDatabaseHelper.getBaseAssetValues();
        while (data3.moveToNext()) {
            list2.add(data3.getString(0));
            baseAssets.setText(data3.getString(1));
            liquidAssets.setText(data3.getString(2));
            totalAssets.setText(data3.getString(4));
        }
        if(list2.size()<1) {
            mDatabaseHelper.addBaseAssetData(0.00, 0.00, 0);
            baseAssets.setText("$0.00");
            liquidAssets.setText("$0.00");
            totalAssets.setText("$0.00");
        }


        Cursor data2 = mDatabaseHelper.getAllItemNames();
        while (data2.moveToNext()) {
            list1.add(data2.getString(0));
        }

        /*
        double base = 100.00;
        String baseStr = Double.toString(base);
        baseAssets.setText(baseStr);

        double liquid = 100.00;
        String liquidStr = Double.toString(liquid);
        liquidAssets.setText(liquidStr);

        double totalAssetsDouble = base + liquid;
        String totalAssetsStr = Double.toString(totalAssetsDouble);
        totalAssets.setText(totalAssetsStr);
        */

        /*
        itemNameText = itemName.getText().toString();
        purchasePriceText = purchasePrice.getText().toString();
        projValueText = projValue.getText().toString();
        */
        // totalAssets = baseAssets + totalItemsOwnedValue + liquidAssets

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                0, 0);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_screen:
                Intent intent = new Intent(HomeScreen.this, HomeScreen.class);
                startActivity(intent);
                break;
            case R.id.nav_inventory:
                intent = new Intent(HomeScreen.this, currentInventory.class);
                startActivity(intent);
                break;
            case R.id.nav_already_sold:
                intent = new Intent(HomeScreen.this, alreadySold.class);
                startActivity(intent);
                break;
            case R.id.nav_all_items:
                intent = new Intent(HomeScreen.this, allItemsList.class);
                startActivity(intent);
                break;
            case R.id.nav_list_item:
                intent = new Intent(HomeScreen.this, itemAdd.class);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        SubMenu sub = menu.addSubMenu("Sell Item");
        for (int i=0; i<list1.size(); i++) {
            sub.add(0, i, Menu.NONE, list1.get(i).toString());
        }
        menu.add("Modify Base Assets");
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        for (int i=0; i< list1.size(); i++) {
            if (i == item.getItemId()) {
                Toast.makeText(getApplicationContext(), list1.get(i).toString(), Toast.LENGTH_SHORT).show();
            }
        }
        if(item.getTitle().equals("Modify Base Assets")) {
            Intent intent = new Intent(HomeScreen.this, baseAssets.class);
            startActivity(intent);
        }
        return true;
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getItemsOwned();
    }

}
