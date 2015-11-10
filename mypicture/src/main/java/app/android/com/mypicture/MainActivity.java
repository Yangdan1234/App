package app.android.com.mypicture;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RotatView rotatView=(RotatView)findViewById(R.id.myRotatView);
        rotatView.setRotatDrawableResource(R.drawable.cycle);

    }
}
