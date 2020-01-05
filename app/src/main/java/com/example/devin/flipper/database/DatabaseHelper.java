package com.example.devin.flipper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.Calendar;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "databaseManager";
    //Tables
    private static final String TABLE_NAME = "items_owned_table";
    private static final String TABLE_NAME2 = "platforms_table";
    private static final String TABLE_NAME3 = "base_assets_table";
 //   private static final String TABLE_NAME3 = "tax_table";
    //Table 1 variables
    private static final String COLUMN_ITEM_ID = "ID";
    private static final String COLUMN_DATE_PURCHASED = "datePurchased";
    private static final String COLUMN_PRICE_PURCHASED = "pricePurchased";
    private static final String COLUMN_DAYS_OWNED = "daysOwned";
    private static final String COLUMN_IS_SOLD = "isSold";
    private static final String COLUMN_PRICE_SOLD = "priceSold";
    private static final String COLUMN_PRICE_PROFIT = "priceProfit";
    private static final String COLUMN_PROJECTED_PRICE = "projPrice";
    private static final String COLUMN_PROJECTED_VALUE = "projValue";
    private static final String COLUMN_DATE_SOLD = "dateSold";
    private static final String COLUMN_ITEM_NAME = "itemName";
    private static final String COLUMN_PLATFORM_TO_SELL_ON = "platformToSellOn";
    private static final String COLUMN_PERCENTAGE_OF_CUT_TAKEN = "percentageOfCutTaken";
    //Table 2 variables
    private static final String COLUMN_PLATFORM_ID = "platID";
    private static final String COLUMN_PLATFORM_NAME = "platformName";
    private static final String COLUMN_SALES_TAX = "salesTax";
    private static final String COLUMN_PERCENTAGE_CUT = "percentageCut";
    //Table 4 variables
    private static final String COlUMN_SALES_TAX_QUARTER = "salesTaxQuarter";
    private static final String COLUMN_SALES_TAX_PER_QUARTER = "salesTaxPerQuarter";
    //Table 3 variables
    private static final String COLUMN_BASE_ASSETS = "baseAssets";
    private static final String COLUMN_LIQUID_ASSETS = "liquidAssets";
    private static final String COLUMN_OWNED_ITEM_ASSETS = "ownedItemAssets";
    private static final String COLUMN_TOTAL_ASSETS = "totalAssets";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 7);

    }

    private static final String createTable = "CREATE TABLE " + TABLE_NAME + " "
            + " ( " + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_ITEM_NAME + " TEXT, "
            + COLUMN_DATE_PURCHASED + " TEXT, "
            + COLUMN_PRICE_PURCHASED + " REAL, "
            + COLUMN_PROJECTED_PRICE + " REAL, "
            + COLUMN_PROJECTED_VALUE + " REAL, "
            + COLUMN_IS_SOLD + " BOOLEAN, "
            + COLUMN_PRICE_SOLD + " REAL, "
            + COLUMN_PRICE_PROFIT + " REAL, "
            + COLUMN_DATE_SOLD + " TEXT ) ";

    private static final String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " "
            + "( platID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLATFORM_NAME + " TEXT, " +
            COLUMN_SALES_TAX + " REAL, " +
            COLUMN_PERCENTAGE_CUT + " REAL )";

//    private static final String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " "
    private static final String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " "
        + "( platID INTEGER PRIMARY KEY AUTOINCREMENT, " +
        COLUMN_BASE_ASSETS + " REAL, " +
        COLUMN_LIQUID_ASSETS + " REAL, " +
        COLUMN_OWNED_ITEM_ASSETS + " REAL, " +
        COLUMN_TOTAL_ASSETS + " REAL )";

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
        db.execSQL(createTable3);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME3);
        onCreate(db);
    }

    public boolean addItemOwnedData(String itemName, String datePurchased, double pricePurchased, double projPrice, double projValue) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, itemName);
        contentValues.put(COLUMN_DATE_PURCHASED, datePurchased);
        contentValues.put(COLUMN_PRICE_PURCHASED, pricePurchased); //What we bought it for
        contentValues.put(COLUMN_PROJECTED_PRICE, projPrice);       //What we think it's worth
        contentValues.put(COLUMN_PROJECTED_VALUE, projValue);       //The potental profit...
        contentValues.put(COLUMN_IS_SOLD, "N");
        contentValues.put(COLUMN_PRICE_SOLD, 0);
        contentValues.put(COLUMN_PRICE_PROFIT, 0);
        contentValues.put(COLUMN_DATE_SOLD, 0);

        Log.d(TAG, "addData: Adding " + itemName + " to " + TABLE_NAME);

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addPlatformData(String platformName, int salesTax, int percentageCut) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_PLATFORM_NAME, platformName);
        contentValues.put(COLUMN_SALES_TAX, salesTax);
        contentValues.put(COLUMN_PERCENTAGE_CUT, percentageCut);

        Log.d(TAG, "addData: Adding " + platformName + " to " + TABLE_NAME2);

        long result = db.insert(TABLE_NAME2, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public boolean addBaseAssetData(double baseAssets, double liquidAssets, double totalAssets) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_BASE_ASSETS, baseAssets);
        contentValues.put(COLUMN_LIQUID_ASSETS, liquidAssets);
        contentValues.put(COLUMN_TOTAL_ASSETS, totalAssets);

        Log.d(TAG, "addData: Adding " + baseAssets + " to " + TABLE_NAME3);

        long result = db.insert(TABLE_NAME3, null, contentValues);

        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

//    public boolean addSalesTaxData(String )

    public Cursor getItemsOwned() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPlatformInfo() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME2;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemId(String itemName) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_ID + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ITEM_NAME + " = '" + itemName + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemName(String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_NAME + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getPricePurchased(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_PRICE_PURCHASED + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getAllItemNames() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT " + COLUMN_ITEM_NAME + " FROM " + TABLE_NAME + "";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getBaseAssetValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME3;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public void deleteItemRow(String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_NAME + " WHERE "
                + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "deleteName: query: " + query);
        db.execSQL(query);
    }

    public void updateitemName(String itemName, String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME + " SET " + COLUMN_ITEM_NAME +
                " = '" + itemName + "' WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateName: query: " + query);
        db.execSQL(query);
    }

    public void updateIsSold(int itemId) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME
                + " SET " + COLUMN_IS_SOLD + " = " + "'Y'"
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateForPurchase: query: " + query);
        db.execSQL(query);
    }

    public void updatePriceSold(int itemId, double priceSoldVal) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME
                + " SET " + COLUMN_PRICE_SOLD + " = '" + priceSoldVal + "'"
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateForPurchase: query: " + query);
        db.execSQL(query);
    }

    public void updatePriceProfit(int itemId, double priceProfit) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME
                + " SET " +  COLUMN_PRICE_PROFIT + " = '" + priceProfit + "'"
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateForPurchase: query: " + query);
        db.execSQL(query);
    }

    public void updateDateSold(int itemId, String currentDate) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME
                + " SET " + COLUMN_DATE_SOLD + " = " + currentDate
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateForPurchase: query: " + query);
        db.execSQL(query);
    }

    public void updateBaseAssets(double baseAssets) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME3
                + " SET " + COLUMN_BASE_ASSETS + " = '" + baseAssets + "'";
        Log.d(TAG, "updateBaseAssets: query: " + query);
        db.execSQL(query);
    }

    public void updateLiquidAssets(double liquidAssets) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME3
                + " SET " + COLUMN_LIQUID_ASSETS + " = '" + liquidAssets + "'";
        Log.d(TAG, "updateBaseAssets: query: " + query);
        db.execSQL(query);
    }

    public void updateOwnedItemAssetsValues(double ownedItemAssets) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME3
                + " SET " + COLUMN_OWNED_ITEM_ASSETS + " = '" + ownedItemAssets + "'";
        Log.d(TAG, "updateBaseAssets: query: " + query);
        db.execSQL(query);
    }

    public void updateTotalAssets(double totalAssets) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME3
                + " SET " + COLUMN_TOTAL_ASSETS + " = '" + totalAssets + "'";
        Log.d(TAG, "updateBaseAssets: query: " + query);
        db.execSQL(query);
    }


    /* Update statement hit when an item is sold */
    /*
    public void updateForPurchase(int itemId, double priceSold, double priceProfit, String dateSold) {
        SQLiteDatabase db = this.getWritableDatabase();

        String query = "UPDATE " + TABLE_NAME
                + " SET " + COLUMN_IS_SOLD + " = " + "'Y'"
                + ", " + COLUMN_PRICE_SOLD + " = '" + priceSold  + "'"
                + ", " + COLUMN_PRICE_PROFIT + " = '" + priceProfit + "'"
                + ", " + COLUMN_DATE_SOLD + " = '" + dateSold + "'"
                + " WHERE " + COLUMN_ITEM_ID + " = '" + itemId + "'";
        Log.d(TAG, "updateForPurchase: query: " + query);
        db.execSQL(query);
    }
*/

}