package rohit.bman.thelazybaboons;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import rohit.bman.thelazybaboons.models.Domain;

public class DbHelper extends SQLiteOpenHelper {

    public DbHelper(Context context) {
        super(context, "dbb", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists domains('id' integer, 'name' text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public int getIdOfDomain(String domainName) {

        int id = 0;

        Cursor cursor = getReadableDatabase().rawQuery("select id from domains where name = ?", new String[]{domainName});
        if (cursor.moveToFirst()) {
            id = cursor.getInt(0);
        }

        cursor.close();

        return id;

    }

    public List<String> getDomainNames() {

        List<String> domainNames = new ArrayList<>();

        Cursor cursor = getReadableDatabase().rawQuery("select name from domains", null);

        while (cursor.moveToNext()) {
            domainNames.add(cursor.getString(0));
        }

        cursor.close();

        return domainNames;
    }

    public boolean saveDomains(List<Domain> domains) {

        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();

        ContentValues contentValues = new ContentValues();
        for (Domain domain :
                domains) {

            contentValues.put("id", domain.getId());
            contentValues.put("name", domain.getDomain());

            long numRows = db.insert("domains", null, contentValues);

            if (numRows == -1) {
                db.endTransaction();
                return false;
            }
        }

        db.setTransactionSuccessful();
        db.endTransaction();

        return true;

    }


}
