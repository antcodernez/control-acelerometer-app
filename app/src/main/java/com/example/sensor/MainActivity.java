package com.example.sensor;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    private SensorManager sensorManager;
    private Sensor proximitySensor;
    private RelativeLayout relativeLayout;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        relativeLayout = findViewById(R.id.relativeLayout);
        textView = findViewById(R.id.textView);

        // Inicializar el SensorManager y el sensor de proximidad
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        proximitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);

        // Verificar si el dispositivo tiene un sensor de proximidad
        if (proximitySensor == null) {
            textView.setText("Este dispositivo no tiene un sensor de proximidad.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Registrar el SensorEventListener para el sensor de proximidad
        if (proximitySensor != null) {
            sensorManager.registerListener(this, proximitySensor, SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Desregistrar el SensorEventListener cuando la actividad está en pausa
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        float distance = event.values[0];

        // Cambiar el color de fondo según la distancia del objeto
        if (distance < proximitySensor.getMaximumRange()) {
            // Objeto cercano
            relativeLayout.setBackgroundColor(Color.RED);
        } else {
            // Objeto lejano
            relativeLayout.setBackgroundColor(Color.GREEN);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // No es necesario manejar cambios en la precisión en este ejemplo
    }
}
