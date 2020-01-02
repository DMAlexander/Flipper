package com.example.devin.flipper.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class alreadySold extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "alreadySold";

    DatabaseHelper mDatabaseHelper;
    private alreadySoldAdapter mAdapter;
    RecyclerView recyclerView;
    public DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_already_sold);

        mDatabaseHelper = new DatabaseHelper(this);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new alreadySoldAdapter(this, getAllItems() );
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new alreadySoldAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onDeleteClick(int position, String recipeName, String recipeId) {

            }
        });
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
        AlertDialog.Builder builder = new AlertDialog.Builder(alreadySold.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, recipeName, recipeId);
                Toast.makeText(alreadySold.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(alreadySold.this, "Sorry.", Toast.LENGTH_SHORT).show();
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
            case R.id.nav_inventory:
                Intent intent = new Intent(alreadySold.this, currentInventory.class);
                startActivity(intent);
            case R.id.nav_already_sold:
                intent = new Intent(alreadySold.this, alreadySold.class);
                startActivity(intent);
            case R.id.nav_all_items:
                intent = new Intent(alreadySold.this, allItemsList.class);
                startActivity(intent);
            case R.id.nav_list_item:
                intent = new Intent(alreadySold.this, itemAdd.class);
                startActivity(intent);
            case R.id.nav_item_sold:
                intent = new Intent(alreadySold.this, itemSold.class);
                startActivity(intent);
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
