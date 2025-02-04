package com.xanthenite.isining.composeapp.ui.activities

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import com.xanthenite.isining.R
import com.xanthenite.isining.composeapp.navigation.RootNavGraph
import com.xanthenite.isining.composeapp.ui.theme.ISiningTheme
import com.xanthenite.isining.core.preference.PreferenceManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity()
{
    @Inject
    lateinit var preferenceManager : PreferenceManager

    override fun onCreate(savedInstanceState : Bundle?)
    {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContent {
            ISiningMain()
        }
        observeUiTheme()
    }

    @Composable
    private fun ISiningMain() {
        val darkMode by preferenceManager.uiModeFlow.collectAsState(initial = isSystemInDarkTheme())

        ISiningTheme(darkTheme = darkMode) {
            Surface {
                RootNavGraph(navController = rememberNavController())
            }
        }
    }

    private fun observeUiTheme() {
        lifecycleScope.launchWhenStarted {
            preferenceManager.uiModeFlow.collect { isDarkMode ->
                val mode = when (isDarkMode) {
                    true -> AppCompatDelegate.MODE_NIGHT_YES
                    false -> AppCompatDelegate.MODE_NIGHT_NO
                }
                AppCompatDelegate.setDefaultNightMode(mode)
            }
        }
    }
}