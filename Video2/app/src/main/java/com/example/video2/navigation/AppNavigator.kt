package com.example.video2.navigation

import androidx.fragment.app.Fragment
import com.example.core.navigation.Navigator

class AppNavigator : Navigator {
    override fun getMoviesMainFragment(): Fragment = MoviesMainFragment()
    override fun getMovieDetailsFragment(imdbID: String): Fragment = MovieDetailsFragment.newInstance(imdbID)
}
