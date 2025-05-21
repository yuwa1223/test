package jp.ac.gifu_u.info.watanabe.myapplication_2025_0423;



import android.content.Context;
import android.hardware.Camera;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager manager;
    private Sensor lightSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // ⚠️ 必ず super.onCreate の前に呼び出す
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);

        // センサー初期化（必要に応じて有効に）
        manager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        if (manager != null) {
            lightSensor = manager.getDefaultSensor(Sensor.TYPE_LIGHT);
        }

        // カメラビューを表示
        CameraView camView = new CameraView(this);
        setContentView(camView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (manager != null && lightSensor != null) {
            manager.registerListener(this, lightSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (manager != null) {
            manager.unregisterListener(this);
        }
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_LIGHT) {
            float intensity = event.values[0];
            // ここで TextView に反映させたい場合は、別のレイアウト設計が必要
            System.out.println("光の強さ: " + intensity + " lx");
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // 使わない場合はそのままでOK
    }

    // カメラビュークラス
    public class CameraView extends SurfaceView implements SurfaceHolder.Callback {
        private Camera cam;
        private SurfaceHolder holder;

        public CameraView(Context context) {
            super(context);
            holder = getHolder();
            holder.addCallback(this);
            // 非推奨のため削除
            // holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            cam = Camera.open(0);
            try {
                cam.setPreviewDisplay(holder);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            if (cam != null) {
                cam.startPreview();
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if (cam != null) {
                cam.setPreviewCallback(null);
                cam.stopPreview();
                cam.release();
                cam = null;
            }
        }
    }
}


