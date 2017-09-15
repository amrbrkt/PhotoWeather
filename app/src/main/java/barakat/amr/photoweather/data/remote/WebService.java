package barakat.amr.photoweather.data.remote;

import barakat.amr.photoweather.data.model.Weather;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface WebService {
    @GET("weather/")
    Call<Weather> getWeather(@Query("lat") double lat, @Query("lon") double lon);
}
