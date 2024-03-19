package com.kostyarazboynik.customviewclock

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kostyarazboynik.customviewclock.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(
            ActivityMainBinding.inflate(layoutInflater).frameContent.id,
            MainFragment.newInstance()
        )
        transaction.commit()
    }
}