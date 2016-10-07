package chatos.camp.noschatos;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import javax.inject.Inject;

import butterknife.ButterKnife;
import chatos.camp.noschatos.adapters.ImageAdapter;
import chatos.camp.noschatos.misc.GridAutofitLayoutManager;
import chatos.camp.noschatos.misc.LayoutManagerType;
import chatos.camp.noschatos.model.Channel;
import chatos.camp.noschatos.model.network.NetworkServiceManager;


public class ChannelListActivity extends EventBaseActivity {


    @Inject
    NetworkServiceManager mNetworkServiceManager;

    @Inject
    Channel mChannel;

    private MenuItem menuGrid;
    private MenuItem menuList;
    private RecyclerView mRecyclerView;
    private ImageAdapter itemAdapter;
    private Boolean actionList = null;
    private Boolean actionGrid = null;
    private RecyclerView.LayoutManager mLayoutManager;
    protected LayoutManagerType mCurrentLayoutManagerType;
    private ProgressBar progressBar;

    private static final String KEY_LAYOUT_MANAGER = "layoutManager";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Channels");

        if (actionList == null) {
            actionList = Boolean.FALSE;
        }
        if (actionGrid == null) {
            actionGrid = Boolean.TRUE;
        }
        itemAdapter = new ImageAdapter(this);

        progressBar = ButterKnife.findById(this, R.id.progress_bar);
        mRecyclerView = ButterKnife.findById(this, R.id.recycler_list);
        mRecyclerView.setHasFixedSize(true);


        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;

        if (savedInstanceState != null) {
            // Restore saved layout manager type.
            mCurrentLayoutManagerType = (LayoutManagerType) savedInstanceState
                    .getSerializable(KEY_LAYOUT_MANAGER);
        }

        mRecyclerView.setAdapter(itemAdapter);


        setRecyclerViewLayoutManager(mCurrentLayoutManagerType);

        progressBar.setVisibility(View.VISIBLE);

        Log.i("_DEBUG", "network service manager : " + mNetworkServiceManager);
        if (mNetworkServiceManager != null) {
            mNetworkServiceManager.getChannels();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    public void handleClickAtRecyclerItem(ImageAdapter.ViewHolder pViewHolder) {
        Toast.makeText(this, "start the room chat for this channel", Toast.LENGTH_SHORT).show();
    }


    /**
     * Set RecyclerView's LayoutManager to the one given.
     *
     * @param layoutManagerType Type of layout manager to switch to.
     */
    public void setRecyclerViewLayoutManager(LayoutManagerType layoutManagerType) {
        int scrollPosition = 0;

        // If a layout manager has already been set, get current scroll position.
        if (mRecyclerView != null) {
            if (mRecyclerView.getLayoutManager() != null) {
                scrollPosition = ((LinearLayoutManager) mRecyclerView.getLayoutManager())
                        .findFirstCompletelyVisibleItemPosition();
            }
        }

        if (itemAdapter == null) Log.e("_DEBUG_", "adapter a null");

        switch (layoutManagerType) {
            case GRID_LAYOUT_MANAGER:
                mLayoutManager = new GridAutofitLayoutManager(this, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 114,
                        getResources().getDisplayMetrics()));
                mCurrentLayoutManagerType = LayoutManagerType.GRID_LAYOUT_MANAGER;
                if (itemAdapter != null) {
                    itemAdapter.setIsGrid(true);
                }
                break;
            case LINEAR_LAYOUT_MANAGER:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                if (itemAdapter != null) {
                    itemAdapter.setIsGrid(false);
                }
                break;
            default:
                mLayoutManager = new LinearLayoutManager(this);
                mCurrentLayoutManagerType = LayoutManagerType.LINEAR_LAYOUT_MANAGER;
                if (itemAdapter != null) {
                    itemAdapter.setIsGrid(false);
                }
        }

        if (mRecyclerView != null) {
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.scrollToPosition(scrollPosition);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_channel, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menuGrid = menu.findItem(R.id.action_grid).setVisible(actionGrid);
        menuList = menu.findItem(R.id.action_list).setVisible(actionList);

        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_grid:
                setRecyclerViewLayoutManager(LayoutManagerType.GRID_LAYOUT_MANAGER);
                item.setVisible(false);
                actionList = true;
                actionGrid = false;

                menuGrid.setVisible(actionGrid);
                menuList.setVisible(actionList);
                break;
            case R.id.action_list:
                setRecyclerViewLayoutManager(LayoutManagerType.LINEAR_LAYOUT_MANAGER);
                item.setVisible(false);
                actionList = false;
                actionGrid = true;

                menuGrid.setVisible(actionGrid);
                menuList.setVisible(actionList);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
