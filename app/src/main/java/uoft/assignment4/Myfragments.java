package uoft.assignment4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;

/**
 * Created by Christine on 16-02-12.
 */

public class Myfragments extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_INFO = "section_number";
    private String p_name;
    private String p_bio;
    private String p_pic;


    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static final Myfragments newInstance(String[] info) {
        Myfragments fragment = new Myfragments();
        Bundle args = new Bundle();
        args.putStringArray(ARG_INFO, info);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        final Bundle bundle = getArguments();
        String[] info = bundle.getStringArray(ARG_INFO);
        p_name = info[0];
        p_bio = info[1];
        p_pic = info[2];
        TextView name_text = (TextView) rootView.findViewById(R.id.person_name);
        TextView bio_text = (TextView) rootView.findViewById(R.id.person_bio);
        ImageView image = (ImageView) rootView.findViewById(R.id.person_picture);
        name_text.setText(p_name);
        bio_text.setText(p_bio);
        new DownloadImageTask(image).execute("http://www.eecg.utoronto.ca/~jayar/" + p_pic);
        Button b1 = (Button)rootView.findViewById(R.id.dont_show_again);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConfirmFragment dialog = new ConfirmFragment();
                dialog.setArguments(bundle);
                dialog.show(getFragmentManager(), "dialog");
            }
        });
        Button b2 = (Button)rootView.findViewById(R.id.more_info);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),Webview.class);
                intent.putExtra("query",p_name);
                startActivity(intent);
            }
        });
        return rootView;
    }

    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;
        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }
        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }
        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public class MoreInfo extends AsyncTask<Void,Void,Void>{
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }
    }
}