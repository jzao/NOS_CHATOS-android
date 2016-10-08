package chatos.camp.noschatos.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import chatos.camp.noschatos.ChannelListActivity;
import chatos.camp.noschatos.R;
import chatos.camp.noschatos.model.Channel;

/**
 * Created by joaozao on 07/10/16.
 */

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private final Context mContext;
    private boolean mIsGrid;
    private List<Channel> mDataList;

    public ItemAdapter(Context pContext) {
        mContext = pContext;
        mDataList = new ArrayList<>();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(viewType == 0 ? R.layout.channel_list_content : R.layout.channel_grid_content, parent, false);
        final ViewHolder viewHolder = new ViewHolder(v);
        viewHolder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View pView) {
                ((ChannelListActivity) mContext).handleClickAtRecyclerItem(viewHolder);
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        int colorResID = mContext.getResources().getColor(R.color.secondaryBackground);
        holder.mItem = mDataList.get(position);
        String iconUrl = holder.mItem.getChannelUri();

        Transformation transformation = new RoundedTransformationBuilder().borderColor(colorResID).borderWidthDp((float) 0.5).cornerRadiusDp(4).oval(false).build();
        final ArrayList<Transformation> nonEmpty = new ArrayList<>();
        nonEmpty.add(transformation);

        Log.i("ola", "is Grid  : " + mIsGrid);
        Picasso.with(mContext).load(iconUrl).transform(nonEmpty).error(R.drawable.beagle).fit().centerInside().into(holder.getIcon());

        holder.getName().setText(mDataList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return mDataList.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (mIsGrid) return 1;
        else return 0;
    }

    public void addItemAtTail(Channel pItem) {
        mDataList.add(pItem);
        if (mContext instanceof ChannelListActivity) {
            ((ChannelListActivity) mContext).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyItemInserted(getItemCount() - 1);
                }
            });
        }
    }

    public void setIsGrid(boolean pIsGrid) {
        mIsGrid = pIsGrid;
    }


    /**
     * Provide a reference to the type of views that you are using (custom ViewHolder)
     */
    public static class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageViewIcon;
        private TextView mTextViewName;
        private View mView;
        public Channel mItem;

        public ViewHolder(View view) {
            super(view);
            mImageViewIcon = ButterKnife.findById(view, R.id.item_icon);
            mTextViewName = ButterKnife.findById(view, R.id.item_name);
            mView = view;
        }

        public ImageView getIcon() {
            return mImageViewIcon;
        }

        public TextView getName() {
            return mTextViewName;
        }

        public View getView() {
            return mView;
        }

        @Override
        public String toString() {
            return "ViewHolder{" +
                    "mImageViewIcon=" + mImageViewIcon +
                    ", mTextViewName=" + mTextViewName +
                    ", mView=" + mView +
                    ", mItem=" + mItem +
                    '}';
        }
    }
}