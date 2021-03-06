package com.example.devin.flipper.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class currentInventory extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "currentInventoryScreen";

    DatabaseHelper mDatabaseHelper;
    private currentInventoryAdapter mAdapter;
    RecyclerView recyclerView;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_inventory_screen);

        mDatabaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new currentInventoryAdapter(this, getAllItems() );
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new currentInventoryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position, String recipeName, String recipeId) {

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

    public void removeItem(int positon, String recipeName, String recipeId) {

        Log.d(TAG, "recipeId: " + recipeId + " and recipeName name is: " + recipeName);

        //    mDatabaseHelper.deleteExportedRecipeRow(recipeId);

        mAdapter.notifyItemChanged(positon);
        mAdapter.notifyDataSetChanged();
        finish();
        overridePendingTransition(0, 0);
        startActivity(getIntent());
        overridePendingTransition(0,0);
        toastMessage("Removed from database");
    }

    public void makeDeleteDialog(final int position, final String recipeName, final String recipeId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(currentInventory.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, recipeName, recipeId);
                Toast.makeText(currentInventory.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(currentInventory.this, "Sorry.", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();

    }
    private void toastMessage (String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private Cursor getAllItems() {
        return mDatabaseHelper.getItemsOwned();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_home_screen:
                Intent intent = new Intent(currentInventory.this, HomeScreen.class);
                startActivity(intent);
                break;
            case R.id.nav_inventory:
                intent = new Intent(currentInventory.this, currentInventory.class);
                startActivity(intent);
                break;
            case R.id.nav_already_sold:
                intent = new Intent(currentInventory.this, alreadySold.class);
                startActivity(intent);
                break;
            case R.id.nav_all_items:
                intent = new Intent(currentInventory.this, allItemsList.class);
                startActivity(intent);
                break;
            case R.id.nav_list_item:
                intent = new Intent(currentInventory.this, itemAdd.class);
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
