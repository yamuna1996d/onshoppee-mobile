package com.onshop.yamuna.onshoppee.Databases;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;

import com.onshop.yamuna.onshoppee.Models.Order;
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.List;

public class Database extends SQLiteAssetHelper {
    private static final String DB_NAME = "SpicesCartDB.db";
    private static final int DB_VER = 1;

    public Database(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    //Method foe get Cart List
    public List<Order> getCarts(){

        SQLiteDatabase db = getReadableDatabase();
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();

        String[] sqlSelect = {"Spiceid", "Spicename", "Quantity", "Price"};
        String sqlTable = "FoodOrder";
        qb.setTables(sqlTable);
        Cursor c = qb.query(db, sqlSelect, null, null, null,null, null);

        final List<Order> result = new ArrayList<>();
        if (c.moveToFirst()){
            do{
                result.add(new Order(c.getString(c.getColumnIndex("Spiceid")),
                        c.getString(c.getColumnIndex("Spicename")),
                        c.getString(c.getColumnIndex("Quantity")),
                        c.getString(c.getColumnIndex("Price"))
                ));
            }while (c.moveToNext());
        }
        return result;
    }

    //Method for add value to cart
    public void addToCart(Order order){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("INSERT INTO FoodOrder(Spiceid, Spicename, Quantity, Price) VALUES ('%s', '%s', '%s', '%s');",
                order.getSpiceid(),
                order.getSpicename(),
                order.getQuantity(),
                order.getPrice());
        db.execSQL(query);
    }

    //
    //Method for Clear value from cart
    public void cleanCart(){
        SQLiteDatabase db = getReadableDatabase();
        String query = String.format("DELETE FROM FoodOrder");
        db.execSQL(query);
    }
}

