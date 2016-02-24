package uoft.assignment4;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;

public class Search extends FragmentActivity {
    private DatabaseHelper dbhelper=null;
    public ArrayList<String> name= new ArrayList<String>();
    public ArrayList<String> bio= new ArrayList<String>();
    public ArrayList<String> pic= new ArrayList<String>();
    public List<Fragment> fList = new ArrayList<Fragment>();
    Activity act;
    Context ctx;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        act = this;
        ctx = this;
        dbhelper = new DatabaseHelper(this);
        new load_data().execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        new load_data().execute();
        //notifyDataSetChanged();
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> my_fragments;
        public SectionsPagerAdapter(FragmentManager fm, List<Fragment> fgs) {
            super(fm);
            this.my_fragments = fgs;
        }
        @Override
        public Fragment getItem(int position) {return this.my_fragments.get(position);}

        @Override
        public int getCount() {return this.my_fragments.size();}

    }
    private class load_data extends AsyncTask<Void, Void, List<Fragment>> {
        @Override
        protected List<Fragment> doInBackground(Void... para) {
            SQLiteDatabase db = dbhelper.getReadableDatabase();

            String selectQuery = "SELECT  * FROM " + dbhelper.TABLE;
            Cursor cursor = db.rawQuery(selectQuery, null);
            name.clear();
            bio.clear();
            pic.clear();
            if (cursor.moveToFirst()) {
                do {
                    name.add(cursor.getString(0));
                    bio.add(cursor.getString(1));
                    pic.add(cursor.getString(2));
                } while (cursor.moveToNext());
            }
            db.close();
            fList.clear();
            for(int i=0;i<name.size();i++){
                String[] info = new String[3];
                info[0] = name.get(i);
                info[1] = bio.get(i);
                info[2] = pic.get(i);
                fList.add(Myfragments.newInstance(info));
            }
            return fList;
        }
        @Override
        protected void onPostExecute(List<Fragment> fgs) {
            super.onPostExecute(fgs);
            //fragments=fgs;
            mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(),fgs);
            mViewPager = (ViewPager) findViewById(R.id.container);
            mViewPager.setAdapter(mSectionsPagerAdapter);
        }
    }

}
