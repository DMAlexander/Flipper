package com.example.devin.flipper.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.devin.flipper.MainActivity;
import com.example.devin.flipper.R;
import com.example.devin.flipper.database.DatabaseHelper;

public class allItemsList extends AppCompatActivity {

    private static final String TAG = "allItemsList";

    DatabaseHelper mDatabaseHelper;
    private allItemsAdapter mAdapter;
    RecyclerView recyclerView;
    Button btnNavigate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_items);

        mDatabaseHelper = new DatabaseHelper(this);
        btnNavigate = (Button) findViewById(R.id.btnNavigate);

        btnNavigate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(allItemsList.this, MainActivity.class);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new allItemsAdapter(this, getAllItems() );
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener(new allItemsAdapter.OnItemClickListener() {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(allItemsList.this);
        builder.setTitle("Delete Ingredient");
        builder.setMessage("Are you sure you want to delete the ingredient?");

        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                removeItem(position, recipeName, recipeId);
                Toast.makeText(allItemsList.this, "Thanks!", Toast.LENGTH_SHORT).show();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(allItemsList.this, "Sorry.", Toast.LENGTH_SHORT).show();
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

}
