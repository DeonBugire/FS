package com.example.core.di

import androidx.fragment.app.Fragment

inline fun <reified T : Dependencies> Fragment.findDependencies(): T {
    return (requireActivity().applicationContext as DependenciesProvider).getDependencies() as T
}