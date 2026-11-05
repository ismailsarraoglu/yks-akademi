package com.yksakademi.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.yksakademi.app.ui.MainViewModel
import com.yksakademi.app.ui.YksAppRoot
import com.yksakademi.app.ui.theme.YksTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val vm: MainViewModel = viewModel()
            val profile by vm.profile.collectAsStateWithLifecycle()
            val systemDark = isSystemInDarkTheme()
            val dark = when (profile?.themeMode) {
                1 -> false
                2 -> true
                else -> systemDark
            }
            YksTheme(darkTheme = dark) {
                YksAppRoot(vm)
            }
        }
    }
}
