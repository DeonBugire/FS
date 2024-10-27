package com.example.video2.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

interface Navigator {
    fun navigateToMovieDetails(fragment: FragmentActivity, imdbID: String)
    fun navigateToMoviesMain(fragment: FragmentActivity)
}