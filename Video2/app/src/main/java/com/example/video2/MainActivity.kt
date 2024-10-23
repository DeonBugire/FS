package com.example.video2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.video2.feature.movielist.presentation.MoviesMainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, MoviesMainFragment())
                .commit()
        }
    }
}