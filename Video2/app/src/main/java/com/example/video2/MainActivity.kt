package com.example.video2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.core.navigation.Navigator
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var navigator: Navigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as VideoApp).appComponent.inject(this)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(android.R.id.content, navigator.getMoviesMainFragment())
                .commit()
        }
    }
}
