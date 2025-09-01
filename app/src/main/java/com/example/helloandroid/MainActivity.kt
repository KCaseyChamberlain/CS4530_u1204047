package com.example.helloandroid

import androidx.appcompat.app.AppCompatActivity
import com.example.helloandroid.databinding.ActivityMainBinding
import android.os.Bundle
import android.view.View
import android.content.Intent
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // attach same handler method to all 5 buttons
        val handler: (View) -> Unit = ::onAnyButtonClick
        with(binding) {
            buttonOne.setOnClickListener(handler)
            buttonTwo.setOnClickListener(handler)
            buttonThree.setOnClickListener(handler)
            buttonFour.setOnClickListener(handler)
            buttonFive.setOnClickListener(handler)
        }
    }

    // Button handler for 5 buttons in one method
    private fun onAnyButtonClick(view: View) {
        val label = (view as Button).text.toString()
        startActivity(
            Intent(this, SecondActivity::class.java).putExtra("label", label)
        )
    }
}