package uoft.assignment4;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper dbhelper=null;
    public ArrayList<String> name= new ArrayList<String>();
    public ArrayList<String> bio= new ArrayList<String>();
    public ArrayList<String> pic= new ArrayList<String>();
    Activity act;
    Context ctx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        act=this;
        ctx=this;
        dbhelper = new DatabaseHelper(this);

        Button b1 = (Button) findViewById(R.id.clear);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ClearTask().execute();
            }
        });
        Button b2 = (Button) findViewById(R.id.populate);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText eText = (EditText) findViewById(R.id.enter_url);
                String str = eText.getText().toString();
                new DownloadTask().execute(str);
            }
        });
        Button b3 = (Button) findViewById(R.id.search);
        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (name.size() > 0) {
                    Intent intent = new Intent(ctx, Search.class);
                    startActivity(intent);

                } else {
                    Toast.makeText(MainActivity.this, "NO DATA", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        Log.i("resume",Integer.toString(name.size()));
    }


    private class DownloadTask extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... Url) {

            try {
                URL url = new URL(Url[0]);
                name.clear();
                bio.clear();
                pic.clear();
                // Read all the text returned by the server
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = in.readLine()) != null) {
                    name.add(line);
                    line = in.readLine();
                    bio.add(line);
                    line = in.readLine();
                    pic.add(line);
                }
                in.close();

            } catch (MalformedURLException e) {
            } catch (IOException e) {
            }
            ContentValues val = new ContentValues();
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            for(int i=0;i<name.size();i++){
                val.clear();
                val.put("name",name.get(i));
                val.put("bio",bio.get(i));
                val.put("pic",pic.get(i));
                db.insertWithOnConflict(dbhelper.TABLE, null, val, SQLiteDatabase.CONFLICT_REPLACE);
            }
            db.close();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "Added to database", Toast.LENGTH_LONG).show();
            super.onPostExecute(aVoid);

        }
    }
    private class ClearTask extends AsyncTask<Void, Void, Void>{
        @Override
        protected Void doInBackground(Void... para) {
            SQLiteDatabase db = dbhelper.getWritableDatabase();
            db.delete(dbhelper.TABLE,null,null);
            db.close();
            name.clear();
            bio.clear();
            pic.clear();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(MainActivity.this, "Information Deleted", Toast.LENGTH_SHORT).show();
            super.onPostExecute(aVoid);
        }
    }
}