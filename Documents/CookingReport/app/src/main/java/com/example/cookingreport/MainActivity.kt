package com.example.cookingreport

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import com.example.cookingreport.R
import com.example.cookingreport.ReadingActivity
import com.example.cookingreport.WritingActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_writing.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        read.setOnClickListener { onReadButtonTapped(it)}
        write.setOnClickListener { onWriteButtonTapped(it) }
        configuration.setOnClickListener { onConfigurationButtonTapped(it) }
    }

    fun onReadButtonTapped (view: View?) {
        val intent = Intent(this, ReadingActivity::class.java)
        startActivity(intent)
    }
    fun onWriteButtonTapped (view: View?) {
        val intent = Intent(this, WritingActivity::class.java)
        startActivity(intent)
    }
    fun onConfigurationButtonTapped (view: View?) {
        val intent = Intent(this, ConfigurationActivity::class.java)
        startActivity(intent)
    }
//    private fun toastMake(message: String, x: Int, y: Int) {
//        val toast = Toast.makeText(applicationContext, message, Toast.LENGTH_LONG)
//        // 位置調整
//        toast.setGravity(Gravity.CENTER, x, y)
//        toast.show()
//    }
}


