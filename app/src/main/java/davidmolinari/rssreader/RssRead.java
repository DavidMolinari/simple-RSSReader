package davidmolinari.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import org.w3c.dom.Document;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by davv on 14/04/2016.
 */
public class RssRead extends AsyncTask<Void, Void, Void>{
    // Message à l'utilisateur lors d'un chargement.
    Context context;
    ProgressDialog progressDialog;

    /**
     * Création d'un string qui contient l'adresse du site à parser. TEST/
     */
    String address = "http://www.zdnet.fr/feeds/rss/actualites/";
    URL url;

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
        //Chargement du xml en background
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputstream  = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputstream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
