package com.example.core.navigation

import androidx.fragment.app.FragmentActivity

interface Navigator {
    fun navigateToMovieDetails(fragment: FragmentActivity, imdbID: String)
    fun navigateToMoviesMain(fragment: FragmentActivity)
}