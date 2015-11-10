package app.android.com.weather;

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

    private TextView textView;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView);
        listView=(ListView)findViewById(R.id.listView);
        parsingFW();
    }

    private void parsingFW() {
        try{
            InputStream in = this.getAssets().open("future.json");
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String json = "";
            String line = null;
            while((line = br.readLine()) != null){
                json += line;
            }
            //Log.v("MainActivity", json);
            String cityName = "";
            String lastUpdate = "";
            String string="";
            List<String> listFutureWeather =null;


            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray = jsonObject.getJSONArray("weather");

            for (int i =0;i<jsonArray.length();i++){
                JSONObject joWeather = jsonArray.getJSONObject(i);
                cityName=joWeather.getString("city_name");
                lastUpdate = joWeather.getString("last_update");

                 string=cityName + "\t\t" + lastUpdate;
                //Log.v("MainActivity", string);
                JSONArray future=joWeather.getJSONArray("future");
                if (future.length()>0){
                    listFutureWeather=new ArrayList<>();
                    for (int j=0;j<future.length();j++){
                        JSONObject joFuture=future.getJSONObject(j);
                        String date=joFuture.getString("date");
                        String day=joFuture.getString("day");
                        String text1=joFuture.getString("text");
                        String high=joFuture.getString("high");
                        String low=joFuture.getString("low");
                        String cop=joFuture.getString("cop");
                        String wind=joFuture.getString("wind");
                        listFutureWeather.add(date+"\t\t"+day+"\n"+
                                text1 +"\n"
                                +high+"\t\t"+low+ "\n"
                                +cop+"\t\t"+wind);
                     //Log.v("MainActivity", listFutureWeather);
                    }
                }
            }
            textView.setText(string);
            ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_activated_1,listFutureWeather);
            listView.setAdapter(stringArrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
