package uoft.assignment4;

import android.app.AlertDialog;
import android.app.Dialog;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.app.DialogFragment;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Christine on 16-02-12.
 */
public class ConfirmFragment extends DialogFragment {
    private String[] info;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.confirm_message)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Bundle bundle = getArguments();
                        info = bundle.getStringArray("section_number");
                        new DeleteInfo().execute();
                        Toast.makeText(getActivity(), "Information Deleted", Toast.LENGTH_LONG).show();
                        getDialog().dismiss();
                    }
                })
                .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {getDialog().dismiss();}
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }
    public class DeleteInfo extends AsyncTask<Void,Void,Void> {
        @Override
        protected Void doInBackground(Void... params) {
            DatabaseHelper helper = new DatabaseHelper(getActivity());
            SQLiteDatabase db = helper.getWritableDatabase();
            db.execSQL("delete from  " + DatabaseHelper.TABLE +
                    " where name=\'" + info[0] + "\'");
            return null;
        }
    }
}