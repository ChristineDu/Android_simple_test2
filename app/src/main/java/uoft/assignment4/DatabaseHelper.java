package uoft.assignment4;

/**
 * Created by Christine on 16-02-10.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.hardware.SensorManager;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="yuqing.db";
    public static final int SCHEMA=1;
    static final String Name="name";
    static final String BIO="bio";
    static final String PICTURE="pic";
    public static final String TABLE="peopleList";

    public DatabaseHelper(Context context) {

        super(context, DATABASE_NAME, null, SCHEMA);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE peopleList (name TEXT PRIMARY KEY, bio TEXT, pic TEXT);");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        throw new RuntimeException("How did we get here?");
    }


}
