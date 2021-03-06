package com.example.myweatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import org.jetbrains.anko.doAsync
import org.json.JSONObject
import java.net.URL

class MainActivity : AppCompatActivity() {

    private var user_field: EditText? = null
    private var main_btn: Button? = null
    private var result_info: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        user_field = findViewById(R.id.user_field)
        main_btn = findViewById(R.id.main_btn)
        result_info = findViewById(R.id.result_info)

        main_btn?.setOnClickListener {
            if(user_field?.text?.toString()?.trim()?.equals("")!!)
                Toast.makeText(this, "Введите город", Toast.LENGTH_LONG).show()
            else{
                val city: String = user_field!!.text.toString()
                val key: String = "5bf643c41eff75cd831c514f22e3c6f6"
                val url: String = "https://api.openweathermap.org/data/2.5/weather?q=$city&APPID=$key"

                doAsync {
                    val apiResponse = URL(url).readText()
                    Log.d("INFO", apiResponse)


                    val weather = JSONObject(apiResponse).getJSONArray("weather")
                    val desc = weather.getJSONObject(0).getString("description")

                    val main = JSONObject(apiResponse).getJSONObject("main")
                    val preTemp = main.getDouble("temp")/10
                    val temp = "$preTemp"


                    result_info?.text = "Temperature: $temp degrees \n$desc"
                }
            }
        }
    }
}