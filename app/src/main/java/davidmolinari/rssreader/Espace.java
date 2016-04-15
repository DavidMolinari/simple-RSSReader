package davidmolinari.rssreader;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by davv on 15/04/2016.
 */
public class Espace extends RecyclerView.ItemDecoration {
    int space;
    public Espace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.left = space;
        outRect.right = space;
        outRect.bottom = space;

        if(parent.getChildLayoutPosition(view) == 0){
            outRect.top = space;
        }
    }
}
