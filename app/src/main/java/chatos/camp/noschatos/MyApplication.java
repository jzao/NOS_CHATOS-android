package chatos.camp.noschatos;

import android.app.Application;
import android.content.Context;

import java.net.Socket;

import dagger.ObjectGraph;

/**
 * Created by joaozao on 07/10/16.
 */

public class MyApplication extends Application {
    private ObjectGraph mObjectGraph;

    @Override
    public void onCreate() {
        super.onCreate();
        mObjectGraph = ObjectGraph.create(new InjectorModule(this));
        SocketManager.getInstance(this).Connect();
    }

    public static MyApplication get(Context context) {
        return (MyApplication) context.getApplicationContext();
    }

    public final void inject(Object object) {
        mObjectGraph.inject(object);
    }
}
