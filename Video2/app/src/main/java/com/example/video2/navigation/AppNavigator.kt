package com.example.video2.navigation

import androidx.fragment.app.FragmentActivity
import com.example.core.navigation.Navigator
import com.example.presentation.MovieDetailsFragment
import com.example.presentation.MoviesMainFragment

class AppNavigator : Navigator {
    override fun navigateToMovieDetails(fragment: FragmentActivity, imdbID: String) {
        val movieDetailsFragment = MovieDetailsFragment.newInstance(imdbID)
        fragment.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, movieDetailsFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun navigateToMoviesMain(fragment: FragmentActivity) {
        val moviesMainFragment = MoviesMainFragment()
        fragment.supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, moviesMainFragment)
            .addToBackStack(null)
            .commit()
    }
}