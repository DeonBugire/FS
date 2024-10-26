package com.example.presentation.navigation

import androidx.fragment.app.Fragment
import com.example.core.navigation.Navigator
import com.example.presentation.MoviesMainFragment

class MovieListNavigator : Navigator {
    override fun getMoviesMainFragment(): Fragment = MoviesMainFragment()
}