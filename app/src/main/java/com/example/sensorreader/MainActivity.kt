package com.example.sensorreader

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.example.sensorreader.databinding.ActivityMainBinding

class MainActivity : SensorEventListener, AppCompatActivity()  {
    lateinit var db: ActivityMainBinding
    val noSensor = "датчик отсутствует"
    lateinit var sm: SensorManager
    var tSensor:Sensor? = null             //температура
    var lSensor:Sensor? = null             //свет
    var pSensor:Sensor? = null           //давление
    var hSensor: Sensor? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBindingUtil.setContentView(this,R.layout.activity_main)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
    }
    override fun onResume() {
        super.onResume()
        tSensor = sm.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE)
        if(tSensor !=null)
            sm.registerListener(this,tSensor,SensorManager.SENSOR_DELAY_GAME)
        lSensor = sm.getDefaultSensor(Sensor.TYPE_LIGHT)
        if(lSensor !=null)
            sm.registerListener(this,lSensor,SensorManager.SENSOR_DELAY_GAME)
        pSensor = sm.getDefaultSensor(Sensor.TYPE_PRESSURE)
        if(pSensor !=null)
            sm.registerListener(this,pSensor,SensorManager.SENSOR_DELAY_GAME)
        hSensor = sm.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY)
        if(hSensor !=null)
            sm.registerListener(this,hSensor,SensorManager.SENSOR_DELAY_GAME)
    }
    override fun onPause() {
        super.onPause()
        sm.unregisterListener(this) }

    override fun onSensorChanged(event: SensorEvent?) {
        var h = 0f
        var t = 0f
        if (tSensor == null) db.temperature.text = "ТЕМПЕРАТУРА: " + noSensor
        else if (event!!.sensor.type == tSensor!!.type) {
            t = event.values[0]; db.temperature.text = "ТЕМПЕРАТУРА: " + t
        }
        if (lSensor == null) db.light.text = "ОСВЕЩЁННОСТЬ: " + noSensor
        else if (event!!.sensor.type == lSensor!!.type) db.light.text =
            "ОСВЕЩЁННОСТЬ: " + event.values[0]
        if (pSensor == null) db.pressure.text = "АТМОСФЕРНОЕ ДАВЛЕНИЕ: " + noSensor
        else if (event!!.sensor.type == pSensor!!.type) db.pressure.text =
            "АТМОСФЕРНОЕ ДАВЛЕНИЕ: " + event.values[0]
        if (hSensor == null) db.humidity.text = "ОТНОСИТЕЛЬНАЯ ВЛАЖНОСТЬ: " + noSensor
        else if (event!!.sensor.type == hSensor!!.type) {
            h = event.values[0]; db.humidity.text = "ОТНОСИТЕЛЬНАЯ ВЛАЖНОСТЬ: " + h
        }
    }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}