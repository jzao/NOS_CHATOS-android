package chatos.camp.noschatos.model.network;

import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by joaozao on 07/10/16.
 */

public interface ChannelAPI {

    @GET("Channel?$format=json")
    Call<JsonObject> getChannels();
}
