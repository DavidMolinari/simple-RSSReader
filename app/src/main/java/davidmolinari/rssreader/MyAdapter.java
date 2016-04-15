package davidmolinari.rssreader;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by davv on 15/04/2016.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    ArrayList<FeedItem>feedItems;
    Context context;
    public MyAdapter(Context context,ArrayList<FeedItem>feedItems){
        this.feedItems=feedItems;
        this.context=context;
    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.costum_row_news_item, parent, false);
        MyViewHolder myHolder = new MyViewHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(MyAdapter.MyViewHolder holder, int position) {
        FeedItem current = feedItems.get(position);
        holder.Title.setText(current.getTitle());
        holder.Description.setText(current.getDescription());
        holder.PubDate.setText(current.getPubDate());
    }


    @Override
    public int getItemCount() {
        return feedItems.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView Title, Description, PubDate;
        ImageView Image;
        public MyViewHolder(View itemView){
            super(itemView);
            Title = (TextView) itemView.findViewById(R.id.textTitle);
            Description = (TextView) itemView.findViewById(R.id.textDescription);
            PubDate = (TextView) itemView.findViewById(R.id.textPubDate);
            Image = (ImageView) itemView.findViewById(R.id.textImageLink);

        }

    }
}
