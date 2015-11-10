package app.android.com.myweather;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class AllActivity extends AppCompatActivity {

    private ListView listView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);

        listView1=(ListView)findViewById(R.id.listView1);
        parsingFuture();
    }

    private void parsingFuture() {
        try {
            InputStream inputStream = this.getAssets().open("all.json");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String json="";
            String line=null;
            if ((line=bufferedReader.readLine())!=null){
                json+=line;
            }
            JSONObject jsonObject=new JSONObject(json);
            JSONArray jsonArray=jsonObject.getJSONArray("weather");

            if (jsonArray.length()>0){
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject jo =jsonArray.getJSONObject(i);

                }

            }


        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
