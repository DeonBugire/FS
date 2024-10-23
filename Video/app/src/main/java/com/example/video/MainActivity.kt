package com.example.video

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.video.presentation.MovieListFragment
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MovieListFragment())
            .commitNow()
    }
}