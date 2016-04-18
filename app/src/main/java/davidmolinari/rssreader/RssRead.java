package davidmolinari.rssreader;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.renderscript.Element;
import android.support.annotation.RequiresPermission;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/**
 * Created by davv on 14/04/2016.
 */
public class RssRead extends AsyncTask<Void, Void, Void>{
    // Message à l'utilisateur lors d'un chargement.
    Context context;
    //String address = "http://www.zdnet.fr/feeds/rss/actualites/";
    Random rand = new Random();
    int  n = rand.nextInt(4) + 0;
    String[] addresses = {"http://www.zdnet.fr/feeds/rss/actualites/",
            "http://www.journaldunet.com/rss/",
            "http://www.01net.com/rss/info/flux-rss/flux-toutes-les-actualites/",
            "http://www.tech2tech.fr/feed/",
            "http://www.01net.com/rss/actualites/jeux/"
    };
    String address = addresses[n];

    ProgressDialog progressDialog;
    ArrayList<FeedItem> feedItems;
    RecyclerView recyclerView;
    /**
     * Création d'un string qui contient l'adresse du site à parser. TEST/
     */
    URL url;


    //Constructeur
    public RssRead(Context context, RecyclerView recyclerView){
        this.recyclerView = recyclerView;
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
        MyAdapter adapter = new MyAdapter(context, feedItems);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.addItemDecoration(new Espace(50));
        recyclerView.setAdapter(adapter);
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
            feedItems = new ArrayList<FeedItem>();

            // On stock channel qui est le premier enfant de Root
            Node channel=root.getChildNodes().item(1);


            NodeList items= channel.getChildNodes();
            for (int i= 0;i<items.getLength();i++){
                 Node currentChild = items.item(i);
            if (currentChild.getNodeName().equalsIgnoreCase("item")){
                FeedItem item = new FeedItem();
                NodeList itemChilds= currentChild.getChildNodes();
                for (int j = 0;j<itemChilds.getLength();j++){
                     Node current= itemChilds.item(j);
                    if (current.getNodeName().equalsIgnoreCase("title")) {
                        item.setTitle(current.getTextContent());
                    } else if (current.getNodeName().equalsIgnoreCase("description")) {
                        item.setDescription(current.getTextContent());
                    } else if (current.getNodeName().equalsIgnoreCase("pubDate")) {
                        item.setPubDate(current.getTextContent());
                    } else if (current.getNodeName().equalsIgnoreCase("link")) {
                        item.setLink(current.getTextContent());

                    } else if (current.getNodeName().equalsIgnoreCase("enclosure")) {
                        // l'URL d'enclosure ( tag Image ) est un attribut. Set to imageLink
                        String url = current.getAttributes().item(0).getTextContent();
                        item.setImageLink(url);
                    }

                }
                feedItems.add(item);

                // Tentative de voir les items dans les logs.
                //Log.d("itemTitle", item.getTitle());
               // Log.d("itemPubDate", item.getPubDate());
               // Log.d("itemLink", item.getLink());
               // Log.d("itemDescription", item.getDescription());
               // Log.d("itemEnclosure", item.getImageLink());


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
