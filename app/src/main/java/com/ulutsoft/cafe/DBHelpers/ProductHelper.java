package com.ulutsoft.cafe.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.ulutsoft.cafe.Objects.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 24.11.2015.
 */
public class ProductHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "POS.db";
    public static final String ITEMS_TABLE = "Products";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PARENT = "parent";
    public static final String COLUMN_CATEGORY = "category";
    public static final String COLUMN_ITEM = "item";
    public static final String COLUMN_PRICE = "price";

    public ProductHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Products (id integer primary key, parent integer, category integer, item text, price real)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ITEMS_TABLE);
        onCreate(db);
    }

    public boolean insert(Product product)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", product.getId());
        contentValues.put("parent", product.getParent());
        contentValues.put("category", product.getCategory());
        contentValues.put("item", product.getItem());
        contentValues.put("price", product.getPrice());
        db.insert(ITEMS_TABLE, null, contentValues);
        return true;
    }

    public Product getProduct(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(ITEMS_TABLE, new String[]{COLUMN_ID, COLUMN_PARENT, COLUMN_CATEGORY, COLUMN_ITEM, COLUMN_PRICE}, COLUMN_ID + "=?", new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
                                //Product(int id, int parent, int category, String item, float price){
        Product product = new Product(Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(1)), Integer.parseInt(cursor.getString(2)),cursor.getString(3), cursor.getFloat(4));
        return product;
    }

    public List<Product> getProducts() {
        List<Product> productList = new ArrayList<Product>();
        String selectQuery = "SELECT  * FROM " + ITEMS_TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setParent(cursor.getInt(1));
                product.setCategory(cursor.getInt(2));
                product.setItem(cursor.getString(3));
                product.setPrice(cursor.getFloat(4));
                productList.add(product);
            } while (cursor.moveToNext());
        }

        return productList;
    }

    public int getProductCount() {
        String countQuery = "SELECT  * FROM " + ITEMS_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        return cursor.getCount();
    }

}
