package com.example.luckyandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.luckyandroidapp.ui.screen.HistoryScreen
import com.example.luckyandroidapp.ui.screen.HomeScreen
import com.example.luckyandroidapp.ui.screen.ProfileScreen
import com.example.luckyandroidapp.ui.theme.primaryColor
import com.example.luckyandroidapp.utils.BillingController
import com.example.luckyandroidapp.utils.isLogin
import com.example.luckyandroidapp.utils.pref
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        pref.isLogin = true
        setContent {
            MaterialTheme {
                BottomNavApp()
            }
        }
    }

    override fun onResume() {
        super.onResume()
//        BillingController.restorePurchases()
    }
}

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

sealed class Screen(val route: String, val title: String, val icon: Int) {
    data object Home : Screen("home", "Home", R.drawable.img_home)
    data object History : Screen("history", "History", R.drawable.img_history)
    data object Profile : Screen("profile", "Profile", R.drawable.img_user)
}

@Preview
@Composable
fun BottomNavApp() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
    ) { paddingValues ->
        NavHostContainer(navController, Modifier.padding(paddingValues))
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val screens = listOf(Screen.Home, Screen.History, Screen.Profile)
    val currentRoute = currentRoute(navController)

    BottomNavigation(backgroundColor = Color.White) {
        screens.forEach { screen ->
            BottomNavigationItem(
                icon = {
                    Image(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title,
                        modifier = Modifier.size(24.dp),
                        colorFilter = ColorFilter.tint(if (currentRoute == screen.route) primaryColor else Color.Gray)
                    )
                },
                label = {
                    Text(
                        screen.title,
                        color = if (currentRoute == screen.route) primaryColor else Color.Gray,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.W600,
                        modifier = Modifier.padding(top = 6.dp)
                    )
                },
                selected = currentRoute == screen.route,
                onClick = { navController.navigate(screen.route) },
                modifier = Modifier.navigationBarsPadding()
            )
        }
    }
}

@Composable
fun NavHostContainer(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(navController, startDestination = Screen.Home.route, modifier) {
        composable(Screen.Home.route) { HomeScreen() }
        composable(Screen.History.route) { HistoryScreen() }
        composable(Screen.Profile.route) { ProfileScreen() }
    }
}