package com.ulutsoft.nurlan.cafe.android;

/**
 * Created by NURLAN on 29.08.2015.
 */
public class ItemsDatasource {

/*
    private SQLiteDatabase database;
    private Database db;

    public ItemsDatasource(Context context) {
        db = new Database(context);
    }

    public void open() throws SQLException {
        database = db.getWritableDatabase();
    }

    public void close() {
        db.close();
    }

    public Items createItem(String comment) {
        ContentValues values = new ContentValues();
        values.put(Database.COLUMN_COMMENT, comment);
        long insertId = database.insert(Database.TABLE_COMMENTS, null,
                values);
        Cursor cursor = database.query(Database.TABLE_COMMENTS,
                allColumns, Database.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Comment newComment = cursorToComment(cursor);
        cursor.close();
        return newComment;
    }

    public void deleteComment(Comment comment) {
        long id = comment.getId();
        System.out.println("Comment deleted with id: " + id);
        database.delete(Database.TABLE_COMMENTS, Database.COLUMN_ID
                + " = " + id, null);
    }

    public List<Comment> getAllComments() {
        List<Comment> comments = new ArrayList<Comment>();

        Cursor cursor = database.query(Database.TABLE_COMMENTS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Comment comment = cursorToComment(cursor);
            comments.add(comment);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return comments;
    }

    private Comment cursorToComment(Cursor cursor) {
        Comment comment = new Comment();
        comment.setId(cursor.getLong(0));
        comment.setComment(cursor.getString(1));
        return comment;
    }
*/
}
