package com.example.baytna.View;

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.baytna.R

class onBoardingScreen : AppCompatActivity() {
    private lateinit var titletxt: TextView
    private lateinit var splashtxt: TextView
    private lateinit var img: ImageView
    private lateinit var btn: Button

    private var currentIndex = 0
    private lateinit var titles: Array<String>
    private lateinit var splashs: Array<String>
    private val onBoardingImgs = arrayOf(
        R.drawable.cleaning_img,
        R.drawable.book_img,
        R.drawable.payment
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_boarding_screen);
        titletxt=findViewById(R.id.titletxt);
        splashtxt=findViewById(R.id.splashtxt);
        img=findViewById(R.id.imageId);
        btn=findViewById(R.id.next_btn);

        titles = resources.getStringArray(R.array.onboarding_titles)
        splashs = resources.getStringArray(R.array.onboarding_splashs)

        updateOnBoardingScreen()
        btn.setOnClickListener {
            currentIndex++

            if (currentIndex < titles.size) {
                updateOnBoardingScreen()
            } else {
                val intent = Intent(this, Welcome::class.java)
                startActivity(intent)
                finish()
            }
        }
    }


    private fun updateOnBoardingScreen() {
        titletxt.setText(titles[currentIndex])
        splashtxt.setText(splashs[currentIndex])
        img.setImageResource(onBoardingImgs[currentIndex])
        if (currentIndex == titles.size - 1) {
            btn.text = "Finish"
        } else {
            btn.text = "Next"
        }
    }
}