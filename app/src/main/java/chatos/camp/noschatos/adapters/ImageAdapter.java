package chatos.camp.noschatos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import chatos.camp.noschatos.R;
import chatos.camp.noschatos.model.Channel;

/**
 * Created by joaozao on 07/10/16.
 */

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ViewHolder> {

    private final Context mContext;
    private boolean mIsGrid;
    private List<Channel> mDataList;

    public ImageAdapter(Context pContext) {
        mContext = pContext;
        mDataList = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.channel_list_content : R.layout.channel_grid_content, parent, false);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }


    public void setIsGrid(boolean pIsGrid) {
        mIsGrid = pIsGrid;
    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {


        public ViewHolder(View view) {
            super(view);

        }
    }
}