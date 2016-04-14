package davidmolinari.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

/**
 * Created by davv on 14/04/2016.
 */
public class RssRead extends AsyncTask<Void, Void, Void>{
    // Message à l'utilisateur lors d'un chargement.
    Context context;
    ProgressDialog progressDialog;
    //Constructeur
    public RssRead(Context context){
        this.context = context;
        ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Chargement, please Meh..");
    }
    @Override
    protected void onPreExecute() {
        progressDialog.show();
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }

    @Override
    protected Void doInBackground(Void... params) {
        return null;
    }
}
