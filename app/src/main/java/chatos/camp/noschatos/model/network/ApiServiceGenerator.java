package chatos.camp.noschatos.model.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by joaozao on 06/10/16.
 */

public class ApiServiceGenerator {
    private static final String API_BASE_URL = "http://nos-brpx.northeurope.cloudapp.azure.com/EPGRepositories/EPGCatalog.svc/";

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .baseUrl(API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = builder.build();
        return retrofit.create(serviceClass);
    }
}
