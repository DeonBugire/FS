package com.example.core.di

import com.example.core.navigation.Navigator

interface Dependencies {
    fun viewModelFactory(): ViewModelFactory
    fun navigator(): Navigator
}