package com.example.core.navigation

import androidx.fragment.app.Fragment

interface Navigator {
    fun getMoviesMainFragment(): Fragment
    fun getMovieDetailsFragment(imdbID: String): Fragment
}