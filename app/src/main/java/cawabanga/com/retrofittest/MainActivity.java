package cawabanga.com.retrofittest;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cawabanga.com.retrofittest.data.model.Query;
import cawabanga.com.retrofittest.data.model.Weather;
import cawabanga.com.retrofittest.remote.WeatherAPI;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.tvCity) TextView tv_city;
    @Bind(R.id.tvDate) TextView tv_date;
    @Bind(R.id.tvTemperature) TextView tv_temperature;
    @Bind(R.id.tvWeather) TextView tv_weather;
    @Bind(R.id.bRefresh) Button b_refresh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.bRefresh) public void onClick_bRefresh(){
        WeatherAPI.Factory.getIstance().getWeather().enqueue(new Callback<Weather>() {
                                                                 @Override
                                                                 public void onResponse(Call<Weather> call, Response<Weather> response) {
                                                                     Query query = response.body().getQuery();
                                                                     tv_temperature.setText(query.getResults().getChannel().getItem().getCondition().getTemp());
                                                                     tv_city.setText(query.getResults().getChannel().getLocation().getCity());
                                                                     tv_date.setText(query.getResults().getChannel().getLastBuildDate());
                                                                     tv_weather.setText(query.getResults().getChannel().getItem().getCondition().getText());
                                                                 }

                                                                 @Override
                                                                 public void onFailure(Call<Weather> call, Throwable t) {
                                                                     Log.e("Failed",t.getMessage());
                                                                 }
                                                             }
        );
    }


    //For Automatically refresh after opening App
    @Override
    protected void onResume() {
        super.onResume();
        onClick_bRefresh();
    }
}
