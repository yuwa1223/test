package jp.ac.gifu_u.info.watanabe.myapplication_2025_0423;

import static androidx.core.content.ContextCompat.getSystemService;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

public class MainActivity extends AppCompatActivity implements SensorEventListener/*extends AppCompatActivity implements View.OnClickListener*/{

    private SensorManager manager;




    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       // EdgeToEdge.enable(this);
        //setContentView(new MyView(this));
        setContentView(R.layout.activity_main);
        manager=(SensorManager)getSystemService(SENSOR_SERVICE);
        /*setContentView(R.layout.activity_main);

        Button b = (Button) findViewById(R.id.button);
        b.setOnClickListener(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
*/
    }

    public void onResume(){
        super.onResume();

        List<Sensor> sensors = manager.getSensorList(Sensor.TYPE_LIGHT);

        if (sensors.size() != 0){
            Sensor sensor = sensors.get(0);
            manager.registerListener(this,sensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }
    @Override
    public void onSensorChanged(SensorEvent arg0){
        if(arg0.sensor.getType()==Sensor.TYPE_LIGHT){
            float intensity = arg0.values[0];

            String str = Float.toString(intensity) + "ルクス";
            TextView textview = (TextView) findViewById(R.id.status_text);

            textview.setText(str);
        }

    }


    public void onAccuracyChanged(Sensor arg0, int arg1) {

    }


    protected void onPause(){
        super.onPause();
        manager.unregisterListener(this);
    }

   /* @Override
    public void onClick(View v){
        Toast.makeText(this, "アプリを終了しました", Toast.LENGTH_SHORT).show();
        finish();
    }*/
}