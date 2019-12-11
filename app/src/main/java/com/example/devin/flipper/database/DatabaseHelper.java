package com.example.devin.flipper.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "databaseManager";
    //Tables
    private static final String TABLE_NAME = "items_owned_table";
    private static final String TABLE_NAME2 = "platforms_table";
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
    //Table 3 variables
    private static final String COlUMN_SALES_TAX_QUARTER = "salesTaxQuarter";
    private static final String COLUMN_SALES_TAX_PER_QUARTER = "salesTaxPerQuarter";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, 1);

    }

    private static final String createTable = "CREATE TABLE " + TABLE_NAME + " "
            + " ( " + COLUMN_ITEM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_DATE_PURCHASED + " DATE, "
            + COLUMN_PRICE_PURCHASED + " REAL, "
            + COLUMN_DAYS_OWNED + " DATE, "
            + COLUMN_IS_SOLD + " BOOLEAN, "
            + COLUMN_PRICE_SOLD + " REAL, "
            + COLUMN_PRICE_PROFIT + " REAL, "
            + COLUMN_DATE_SOLD + " REAL, "
            + COLUMN_ITEM_NAME + " TEXT, "
            + COLUMN_PLATFORM_TO_SELL_ON + " TEXT, "
            + COLUMN_PERCENTAGE_OF_CUT_TAKEN + " TEXT ) ";

    private static final String createTable2 = "CREATE TABLE " + TABLE_NAME2 + " "
            + "( platID INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLATFORM_NAME + " TEXT, " +
            COLUMN_SALES_TAX + " REAL, " +
            COLUMN_PERCENTAGE_CUT + " REAL )";

//    private static final String createTable3 = "CREATE TABLE " + TABLE_NAME3 + " "


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTable);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int il) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_NAME2);
        onCreate(db);
    }

    public boolean addItemOwnedData(String itemName, String datePurchased, double pricePurchased, double projPrice, double projValue, String isSold, String dateSold) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_ITEM_NAME, itemName);
        contentValues.put(COLUMN_DATE_PURCHASED, datePurchased);
        contentValues.put(COLUMN_PRICE_PURCHASED, pricePurchased);
        contentValues.put(COLUMN_PROJECTED_PRICE, projPrice);
        contentValues.put(COLUMN_PROJECTED_VALUE, projValue);
        contentValues.put(COLUMN_IS_SOLD, isSold); //should be 'N' by default
        contentValues.put(COLUMN_DATE_SOLD, dateSold); //should be null by default

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
                + " WHERE " + COLUMN_ITEM_NAME + " = " + itemName + "";
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    public Cursor getItemName(String itemId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELEC " + COLUMN_ITEM_NAME + " FROM " + TABLE_NAME
                + " WHERE " + COLUMN_ITEM_ID + " = " + itemId + "";
        Cursor data = db.rawQuery(query, null);
        return data;
    }


}