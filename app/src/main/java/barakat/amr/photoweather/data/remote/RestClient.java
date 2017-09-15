package barakat.amr.photoweather.data.remote;

import barakat.amr.photoweather.Constants;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestClient {
    private static RestClient client;
    private static WebService mWebService;

    public RestClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        Retrofit restAdapter = new Retrofit.Builder()
                .client(builder.build())
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static RestClient getInstance() {
        if (client == null) {
            client = new RestClient();
        }
        return client;
    }

    public static WebService getWebService() {
        return mWebService;
    }
}
