package davidmolinari.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.Element;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
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
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Chargement, please Meh..");
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        progressDialog.show();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        progressDialog.dismiss();
    }

    @Override
    protected Void doInBackground(Void... params) {
        ProcessXml(getData());

        return null;
    }

    private void ProcessXml(Document data) {
        if (data != null) {
            // On stock l'élément root
            org.w3c.dom.Element root = data.getDocumentElement();

            // On stock channel qui est le premier enfant de Root
            Node channel=root.getChildNodes().item(1);


            NodeList items= channel.getChildNodes();
            for (int i= 0;i<items.getLength();i++){
                 Node currentChild = items.item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("item")){
                NodeList itemChilds= currentChild.getChildNodes();
                for (int j = 0;j<itemChilds.getLength();j++){
                     Node current= itemChilds.item(j);
                Log.d("mehContent",current.getTextContent());
            }
        }
    }
}}




    public Document getData(){
        //Chargement du xml en background
        try {
            url = new URL(address);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            InputStream inputstream  = connection.getInputStream();
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = documentBuilderFactory.newDocumentBuilder();
            Document xmlDoc = builder.parse(inputstream);
            return xmlDoc;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
