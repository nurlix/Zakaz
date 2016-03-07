package com.nurlan.zakaz.DBHelpers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.nurlan.zakaz.Objects.Category;
import com.nurlan.zakaz.Objects.Customer;
import com.nurlan.zakaz.Objects.Order;
import com.nurlan.zakaz.Objects.OrderItem;
import com.nurlan.zakaz.Objects.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NURLAN on 25.11.2015.
 */

public class DataProvider extends SQLiteOpenHelper {

    public static final String DATABASE = "Zakaz.db";

    public DataProvider(Context context) {
        super(context, DATABASE, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("CREATE TABLE Products (Id INTEGER, Parent INTEGER, Item TEXT, Price REAL, isFolder INTEGER)");
        db.execSQL("CREATE TABLE Customers (Id INTEGER, Name TEXT, Phone TEXT, Adres TEXT)");
        db.execSQL("CREATE TABLE Orders (Id INTEGER PRIMARY KEY AUTOINCREMENT, OrderID TEXT, OrderDate Date default (datetime('now','localtime')), Customer INTEGER, OrderDiscount REAL)");
        db.execSQL("CREATE TABLE OrderDetails (Id INTEGER PRIMARY KEY AUTOINCREMENT, OrderID TEXT, ItemId INTEGER, Quantity INTEGER, Price REAL, Discount REAL)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS Customers");
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS OrderDetails");
        onCreate(db);
    }

    public void resetOrders() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS Orders");
        db.execSQL("DROP TABLE IF EXISTS OrderDetails");
        db.execSQL("CREATE TABLE Orders (Id INTEGER PRIMARY KEY AUTOINCREMENT, OrderID TEXT, OrderDate Date default (datetime('now','localtime')), Customer INTEGER, OrderDiscount REAL)");
        db.execSQL("CREATE TABLE OrderDetails (Id INTEGER PRIMARY KEY AUTOINCREMENT, OrderID TEXT, ItemId INTEGER, Quantity INTEGER, Price REAL, Discount REAL)");
    }

    public void FILLCUSTOMERS(List<Customer> customers)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        for(Customer customer : customers) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", customer.getId());
            contentValues.put("Name", customer.getName());
            contentValues.put("Phone", customer.getPhone());
            contentValues.put("Adres", customer.getAdress());
            db.insert("Customers", null, contentValues);
        }
    }

    public void FILLPRODUCTS(List<Product> products)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        for(Product product : products) {
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", product.getId());
            contentValues.put("Parent", product.getParent());
            contentValues.put("Item", product.getItem());
            contentValues.put("Price", product.getPrice());
            contentValues.put("isFolder", product.getIsFolder());
            db.insert("Products", null, contentValues);
        }
    }

    public void AddOrder(Order order)
    {
        if(order.getID() == 0) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("OrderID", order.getOrderID());
            contentValues.put("Customer", order.getCustomerId());
            contentValues.put("OrderDiscount", order.getOrderDiscount());
            db.insert("Orders", null, contentValues);
        }
    }

    public void AddOrderItem(OrderItem orderItem)
    {
        final int FGINSERT = 2;
        final int FGUPDATE = 3;

        String table = "OrderDetails";

        String[] selected = { String.valueOf(orderItem.getID()) };

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("OrderID", orderItem.getOrderID());
        contentValues.put("ItemId", orderItem.getItemId());
        contentValues.put("Quantity", orderItem.getQuantity());
        contentValues.put("Price", orderItem.getPrice());
        contentValues.put("Discount", orderItem.getDiscount());

        if(orderItem.getQuantity() == 0) {
            db.delete(table, " Id = ? ", selected);
        }
        if(orderItem.getFlag() == FGUPDATE) {
            db.update(table, contentValues, " Id =  ? ", selected);
        }
        if(orderItem.getFlag() == FGINSERT) {
            db.insert(table, null, contentValues);
        }
    }

    public List<Customer> GETCUSTOMERS()
    {
        List<Customer> customers = new ArrayList<Customer>();
        String selectQuery = "SELECT * FROM Customers order by Name";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Customer customer = new Customer();
                customer.setId(cursor.getInt(0));
                customer.setName(cursor.getString(1));
                customer.setPhone(cursor.getString(2));
                customer.setAdress(cursor.getString(3));
                customers.add(customer);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return customers;
    }

    public List<Product> GETPRODUCTS(String parent)
    {
        List<Product> products = new ArrayList<Product>();
        if(!parent.isEmpty()) {
            parent = " where isFolder = 0 and parent = " + parent;
        } else {
            parent = " where isFolder = 0 ";
        }
        String selectQuery = "SELECT Id, Parent, Item, Price, isFolder FROM Products " + parent + " order by Item";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Product product = new Product();
                product.setId(cursor.getInt(0));
                product.setParent(cursor.getInt(1));
                product.setItem(cursor.getString(2));
                product.setPrice(cursor.getFloat(3));
                product.setIsFolder(cursor.getInt(4));
                products.add(product);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return products;
    }

    public List<Category> GETCATEGORIES()
    {
        List<Category> categories = new ArrayList<Category>();
        String selectQuery = "SELECT id, item FROM Products where isFolder = 1";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Category category = new Category();
                category.setID(cursor.getInt(0));
                category.setName(cursor.getString(1));
                category.setItems(GETPRODUCTS(cursor.getString(0)));
                categories.add(category);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return categories;
    }

    public List<Order> GETORDERS()
    {
        List<Order> orders = new ArrayList<Order>();
        //String selectQuery = "SELECT odr.id, odr.OrderID, strftime('%d.%m.%Y %H:%M', odr.OrderDate) as OrderDate, cm.Name FROM Orders odr left join Customers cm on odr.Customer = cm.[Id]";
        String selectQuery = "SELECT\n" +
                "odr.id,\n" +
                "odr.[OrderID],\n" +
                "cus.[Name],\n" +
                "strftime('%d.%m.%Y %H:%M', odr.OrderDate) as OrderDate,\n" +
                "(\n" +
                "select\n" +
                "sum(ods.[Price] * ods.[Quantity]) as Total\n" +
                "from OrderDetails ods\n" +
                "where ods.[OrderID] = odr.[OrderID]\n" +
                ") as Total\n" +
                "FROM Orders odr\n" +
                "left join Customers cus on odr.[Customer] = cus.[Id] ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setID(cursor.getInt(0));
                order.setOrderID(cursor.getString(1));
                order.setOrderCustomer(cursor.getString(2));
                order.setOrderDate(cursor.getString(3));
                order.setTotal(Math.round(cursor.getFloat(4)));
                order.setSaved(true);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    public List<Order> ORDERS(int agent)
    {
        List<Order> orders = new ArrayList<Order>();
        String selectQuery = "Select " +
                "ID, " +
                "OrderID, " +
                "OrderDate, " +
                "Customer, " +
                "OrderDiscount " +
                "from " +
                "Orders";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setID(cursor.getInt(0));
                order.setCustomerId(cursor.getInt(3));
                order.setOrderID(cursor.getString(1));
                order.setOrderDate(cursor.getString(2));
                order.setOrderDiscount(cursor.getFloat(4));
                order.setAgent(agent);
                orders.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orders;
    }

    public List<OrderItem> GETDETAILS(String OID)
    {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        String selectQuery = "SELECT od.Id, od.ItemId, od.Quantity, od.Price, pd.Item, od.OrderID FROM OrderDetails od LEFT JOIN Products pd on od.[ItemID] = pd.[Id] where od.OrderID = '" + OID + "'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderItem orderItem = new OrderItem();
                orderItem.setID(cursor.getInt(0));
                orderItem.setItemId(cursor.getInt(1));
                orderItem.setQuantity(cursor.getInt(2));
                orderItem.setPrice(cursor.getFloat(3));
                orderItem.setItem(cursor.getString(4));
                orderItem.setOrderID(cursor.getString(5));
                orderItem.setFlag(1);
                orderItems.add(orderItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderItems;
    }

    public List<OrderItem> DETAILS()
    {
        List<OrderItem> orderItems = new ArrayList<OrderItem>();
        String selectQuery = "Select " +
                "Id, " +
                "OrderID, " +
                "ItemId, " +
                "Quantity, " +
                "Price, " +
                "Discount " +
                "From " +
                "OrderDetails";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                OrderItem orderItem = new OrderItem();
                orderItem.setID(cursor.getInt(0));
                orderItem.setOrderID(cursor.getString(1));
                orderItem.setItemId(cursor.getInt(2));
                orderItem.setQuantity(cursor.getInt(3));
                orderItem.setPrice(cursor.getFloat(4));
                orderItem.setDiscount(cursor.getFloat(5));
                orderItems.add(orderItem);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return orderItems;
    }

    public void DELETE(String table)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + table);
    }

    public int Count(String tablename) {
        int result = 0;
        String countQuery = "SELECT Id FROM " + tablename;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        result = cursor.getCount();
        cursor.close();
        return result;
    }
}
