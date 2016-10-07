package chatos.camp.noschatos;

import android.util.Log;

import chatos.camp.noschatos.event.ShowProgressDialogEvent;
import de.greenrobot.event.EventBus;

/**
 * Created by joaozao on 07/10/16.
 */

public abstract class EventBaseActivity extends BaseActivity{


    //register/unregister for events from the EventBus
    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ola", "unregister Event");
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ola", "register Event");
        EventBus.getDefault().register(this);
    }

    public void onEvent(ShowProgressDialogEvent event) {
        // TODO use this events to show and hide the progress bar
    }

}
