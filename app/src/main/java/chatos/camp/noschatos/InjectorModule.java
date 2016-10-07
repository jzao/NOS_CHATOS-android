package chatos.camp.noschatos;

import android.util.Log;

import javax.inject.Singleton;

import chatos.camp.noschatos.model.network.ApiServiceGenerator;
import chatos.camp.noschatos.model.Channel;
import chatos.camp.noschatos.model.network.NetworkServiceManager;
import dagger.Module;
import dagger.Provides;

/**
 * Created by joaozao on 07/10/16.
 */

@Module(injects = {ChannelListActivity.class}, complete = true)
public class InjectorModule {
    private MyApplication mMyApplication;

    public InjectorModule(MyApplication pMyApplication) {
        mMyApplication = pMyApplication;
    }

    @Provides
    public ApiServiceGenerator provideApiServiceGenerator() {
        return new ApiServiceGenerator();
    }


    @Provides
    public NetworkServiceManager provideChannelServiceManager(ApiServiceGenerator pApiServiceGenerator) {
        Log.i("_DEBUG", "vai criar o network service 1");
        return new NetworkServiceManager(pApiServiceGenerator);
    }

  /*  @Provides
    @Singleton
    public Channel provideChannelManager() {
        return new Channel();
    }
*/
}

