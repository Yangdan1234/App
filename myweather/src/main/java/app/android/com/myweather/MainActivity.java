package app.android.com.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text=(TextView)findViewById(R.id.text);
        listView=(ListView)findViewById(R.id.listView);
        parsingFutureWeather();
    }

    private void parsingFutureWeather() {
        try{
            InputStream in = this.getAssets().open("weather.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String json = "";
            String line = null;
            while((line = br.readLine()) != null){
                json += line;
            }
            String cityName = "";
            String lastUpdate = "";
            List<String> listWeather =null;
            String cityId="";

            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");

            for (int i =0;i<jsonArray.length();i++){
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                cityName=jsonObject1.getString("city_name");
                cityId=jsonObject1.getString("city_id");
                lastUpdate = jsonObject1.getString("last_update");

                JSONArray future=jsonObject1.getJSONArray("future");
                if (future.length()>0){
                    listWeather=new ArrayList<>();
                    for (int j=0;j<future.length();j++){
                        JSONObject jaFuture=future.getJSONObject(j);
                        String date=jaFuture.getString("date");
                        String day=jaFuture.getString("day");
                        String text1=jaFuture.getString("text");
                        String code1=jaFuture.getString("code1");
                        String code2=jaFuture.getString("code2");
                        String high=jaFuture.getString("high");
                        String low=jaFuture.getString("low");
                        String cop=jaFuture.getString("cop");
                        String wind=jaFuture.getString("wind");
                        listWeather.add(date+"\n"+day+"\n"+text1+"\n"
                                +code1+"\n"+code2+"\n"+high+"\n"+low+
                                "\n"+cop+"\n"+wind);

                    }
                }
            }
            text.setText(cityName + "\n" + cityId + "\n" + lastUpdate);
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_activated_1,listWeather);
            listView.setAdapter(arrayAdapter);
            //Log.v("MainActivity", json);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
