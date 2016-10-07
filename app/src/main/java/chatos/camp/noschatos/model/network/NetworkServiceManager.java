package chatos.camp.noschatos.model.network;

import android.util.Log;

import com.google.gson.JsonObject;

import chatos.camp.noschatos.model.Channel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkServiceManager {
    private ApiServiceGenerator mApiServiceGenerator;
    private Channel mModelChannel;
    private ChannelAPI mService;


    public NetworkServiceManager(ApiServiceGenerator pApiServiceGenerator, Channel pModelChannel) {
        Log.i("_DEBUG", "vai criar o network service");
        mApiServiceGenerator = pApiServiceGenerator;
        mModelChannel = pModelChannel;
    }


    public void getChannels() {
        mService = mApiServiceGenerator.createService(ChannelAPI.class);
        Call<JsonObject> getChannels = mService.getChannels();
        getChannels.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("_DEBUG", ""+response.body());
                Log.i("_DEBUG", "is successful ? : "+response.isSuccessful());

            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


}