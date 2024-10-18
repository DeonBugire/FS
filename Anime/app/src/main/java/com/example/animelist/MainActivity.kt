package com.example.animelist

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animelist.presentation.MovieListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, MovieListFragment())
            .commitNow()
    }
}