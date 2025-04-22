package com.example.sensorreader

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
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
    var ttext=""
    var ltext=""
    var ptext=""
    var htext=""
    var sensors="light"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DataBindingUtil.setContentView(this,R.layout.activity_main)
        sm = getSystemService(SENSOR_SERVICE) as SensorManager
        val spinner = findViewById<Spinner>(R.id.spinner)
        val types = resources.getStringArray(R.array.sensorsTypes)

        if (spinner != null) {
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, types)
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>,
                                            view: View, position: Int, id: Long) {
                    if (position==1) {
                        sensors="light"
                        db.text.text=ltext
                    }else if(position==0){
                        sensors="pressure"
                        db.text.text=ptext
                    }else if(position==2){
                        sensors="humidity"
                        db.text.text=htext
                    }else{
                        sensors="temperature"
                        db.text.text=ttext
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
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
        if (tSensor == null) ttext = noSensor
        else if (event!!.sensor.type == tSensor!!.type) {
            t = event.values[0];
            ttext=t.toString()
            if (sensors=="temperature") {
                db.text.text = t.toString()
            }
        }
        if (lSensor == null) ltext = noSensor
        else if (event!!.sensor.type == lSensor!!.type){
            ltext=event.values[0].toString()
            if (sensors=="light") {
                db.text.text =event.values[0].toString()
            }
        }
        if (pSensor == null) ptext =noSensor
        else if (event!!.sensor.type == pSensor!!.type){
            ptext=event.values[0].toString()
            if (sensors=="pressure") {
                db.text.text = event.values[0].toString()
            }
        }
        if (hSensor == null) htext= noSensor
        else if (event!!.sensor.type == hSensor!!.type) {
            h = event.values[0];
            htext=h.toString()
            if (sensors=="humidity") {
                db.text.text = h.toString()
            }
        }
    }
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) { }
}