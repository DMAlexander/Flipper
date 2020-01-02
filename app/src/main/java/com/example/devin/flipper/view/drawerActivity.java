package com.example.devin.flipper.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.devin.flipper.MainActivity;
import com.example.devin.flipper.R;

public class drawerActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public DrawerLayout drawer;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);

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

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_inventory:
                Intent intent = new Intent(drawerActivity.this, currentInventory.class);
                startActivity(intent);
            case R.id.nav_already_sold:
                intent = new Intent(drawerActivity.this, alreadySold.class);
                startActivity(intent);
            case R.id.nav_all_items:
                intent = new Intent(drawerActivity.this, allItemsList.class);
                startActivity(intent);
            case R.id.nav_list_item:
                intent = new Intent(drawerActivity.this, itemAdd.class);
                startActivity(intent);
            case R.id.nav_item_sold:
                intent = new Intent(drawerActivity.this, itemSold.class);
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
