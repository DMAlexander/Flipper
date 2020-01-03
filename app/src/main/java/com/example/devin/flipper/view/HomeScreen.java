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
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

import org.w3c.dom.Text;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mDatabaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new HomeScreenAdapter(this, getAllItems() );
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
            case R.id.nav_item_sold:
                intent = new Intent(HomeScreen.this, itemSold.class);
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

    private Cursor getAllItems() {
        return mDatabaseHelper.getItemsOwned();
    }

}
