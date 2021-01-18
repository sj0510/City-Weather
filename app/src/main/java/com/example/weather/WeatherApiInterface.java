package com.example.weather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherApiInterface {
    @GET("weather")
    Call<Example> getWeather(@Query("q") String cityname, @Query("appid") String ApiKey);
}
