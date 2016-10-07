package chatos.camp.noschatos.model.network;

import android.net.Uri;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import chatos.camp.noschatos.event.RetrieveChannelsEvent;
import chatos.camp.noschatos.model.Channel;
import de.greenrobot.event.EventBus;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NetworkServiceManager {
    private ApiServiceGenerator mApiServiceGenerator;
    private ChannelAPI mService;


    public NetworkServiceManager(ApiServiceGenerator pApiServiceGenerator) {
        Log.i("_DEBUG", "vai criar o network service");
        mApiServiceGenerator = pApiServiceGenerator;
    }


    public void getChannels() {
        mService = mApiServiceGenerator.createService(ChannelAPI.class);
        Call<JsonObject> getChannels = mService.getChannels();
        getChannels.enqueue(new Callback<JsonObject>() {
            @Override
            public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                Log.i("_DEBUG", ""+response.body());
                Log.i("_DEBUG", "is successful ? : "+response.isSuccessful());

                List<Channel> channelsList = new ArrayList<Channel>();

                JsonArray channelsArray = response.body().getAsJsonArray("value");

                for(int i = 0 ; i < channelsArray.size() ; i++){
                    JsonObject jsonChannel = channelsArray.get(i).getAsJsonObject();

                    int id = Integer.parseInt(jsonChannel.get("ChannelId").getAsString());
                    String name = jsonChannel.get("Name").getAsString();
                    String iconUri = jsonChannel.get("IconUri").getAsString();

                    channelsList.add(new Channel(id, name, iconUri));
                }

                EventBus.getDefault().post(new RetrieveChannelsEvent(channelsList));
            }

            @Override
            public void onFailure(Call<JsonObject> call, Throwable t) {

            }
        });
    }


}