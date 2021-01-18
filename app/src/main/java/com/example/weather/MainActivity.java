package com.example.weather;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchView searchViewcity;
    private Button searchBTN;
    private TextView temperature, pressure, humidity, windspeed;

    String ApiKey = "f9dd5d470e6300dc40097b8b844c6e41";
    String ApiUrl = "api.openweathermap.org/data/2.5/weather?id={city id}&appid={your api key}";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchViewcity = findViewById(R.id.searchView);
        searchBTN = findViewById(R.id.btnSearch);
        temperature = findViewById(R.id.tv_temp);
        pressure = findViewById(R.id.tv_pressure);
        humidity = findViewById(R.id.tv_humidity);
        windspeed = findViewById(R.id.tv_windSpeed);


        searchBTN.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == searchBTN){

            String city_input = searchViewcity.getQuery().toString();

            if (TextUtils.isEmpty(city_input)){
                Toast empty = Toast.makeText(MainActivity.this,"Please enter City Name...",Toast.LENGTH_LONG);
                empty.show();
            }
            else {

                getWeather(v);

            }
        }

    }

    public void getWeather(View v){

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https:api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create()).build();

        WeatherApiInterface myApiInterface = retrofit.create(WeatherApiInterface.class);
        Call<Example> exampleCall = myApiInterface.getWeather(searchViewcity.getQuery().toString().trim(),ApiKey);
        exampleCall.enqueue(new Callback<Example>() {
            @Override
            public void onResponse(Call<Example> call, Response<Example> response) {
                if (response.code()==404){
                    Toast.makeText(MainActivity.this,"Please Enter a valid City name",Toast.LENGTH_LONG).show();
                }
                else if (!(response.isSuccessful())){
                    Toast.makeText(MainActivity.this,response.code(),Toast.LENGTH_LONG).show();
                }

                Example mydataMain = response.body();
                Main main = mydataMain.getMain();

                ExampleWind mydataWind = response.body();
                Wind wind = mydataWind.getWind();


                //Temperature
                Double temp = main.getTemp();
                Integer temper = (int)(temp-273.15);
                temperature.setText("Temperature : "+String.valueOf(temper)+"°C");

                //Humidity
                Integer humid = main.getHumidity();
                humidity.setText("Humidity : "+humid+"%");

                //Pressure
                Integer press = main.getPressure();
                pressure.setText("Pressure : "+press+" mb");

                //WindSpeed
                Double windSpeed = wind.getSpeed();
                windspeed.setText("Wind Speed : "+String.valueOf(windSpeed)+" km/h");




            }

            @Override
            public void onFailure(Call<Example> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();


            }
        });
    }


}