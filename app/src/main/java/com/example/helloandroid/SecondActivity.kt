package com.example.helloandroid

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.helloandroid.databinding.ActivitySecondBinding

class SecondActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // show text sent from MainActivity
        val label = intent.getStringExtra("label") ?: ""
        binding.receivedText.text = label

        // back Button to MainActivity
        binding.backButton.setOnClickListener { finish() }
    }
}