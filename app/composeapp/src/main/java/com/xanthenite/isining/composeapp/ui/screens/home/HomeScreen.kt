package com.xanthenite.isining.composeapp.ui.screens.home

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.xanthenite.isining.composeapp.component.ConnectivityStatus
import com.xanthenite.isining.composeapp.component.action.ArScanAction
import com.xanthenite.isining.composeapp.component.action.ThemeSwitchAction
import com.xanthenite.isining.composeapp.component.scaffold.main.HomeTopBar
import com.xanthenite.isining.composeapp.component.scaffold.ISiningScaffold
import com.xanthenite.isining.composeapp.utils.collectState
import com.xanthenite.isining.view.viewmodel.main.HomeViewModel

@Composable
fun HomeScreen(viewModel : HomeViewModel ,
               onNavigateToAr : () -> Unit)
{
    val state by viewModel.collectState()

    val isInDarkMode = isSystemInDarkTheme()

    HomeContent(
        isLoading = state.isLoading ,
        isConnectivityAvailable = state.isConnectivityAvailable,
        onRefresh = { /*TODO*/ } ,
        onToggleTheme = { viewModel.setDarkMode(!isInDarkMode) },
        onClickScanner = onNavigateToAr)

}

@Composable
fun HomeContent(
    isLoading : Boolean,
    isConnectivityAvailable : Boolean?,
    error : String? = null,
    onRefresh : () -> Unit,
    onToggleTheme : () -> Unit,
    onClickScanner : () -> Unit)
{
    ISiningScaffold(
        error = error,
        topAppBar = {
            HomeTopBar(
                actions = {
                    ArScanAction(onClickScanner)
                    ThemeSwitchAction(onToggleTheme)
                })
        },
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isLoading) ,
                onRefresh = onRefresh ,
                swipeEnabled = isConnectivityAvailable == true)
        {
            Column {
                if (isConnectivityAvailable != null) {
                    ConnectivityStatus(isConnectivityAvailable)
                }
            }
        }
    })
}