package co.feip.fefu2025.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import co.feip.fefu2025.presentation.list.RepoListType
import co.feip.fefu2025.presentation.list.RepositoriesScreen
import co.feip.fefu2025.presentation.list.RepositoriesViewModel

 
sealed class Screen(val route: String, val label: String, val icon: @Composable () -> Unit) {
    object Home : Screen("home", "Home", { Icon(Icons.Default.Home, contentDescription = "Home") })
    object Stars : Screen("stars", "My Stars", { Icon(Icons.Default.Star, contentDescription = "My Stars") })
}

val items = listOf(Screen.Home, Screen.Stars)

@Composable
fun AppNavHost(viewModelFactory: ViewModelProvider.Factory) {
    val navController = rememberNavController()
    val sharedViewModel: RepositoriesViewModel = viewModel(factory = viewModelFactory)


    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items.forEach { screen ->
                    NavigationBarItem(
                        icon = { screen.icon() },
                        label = { Text(screen.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            composable(Screen.Home.route) {
                RepositoriesScreen(
                    viewModel = sharedViewModel, 
                    listType = RepoListType.ALL,
                    title = "All Projects"
                )
            }
                    composable(Screen.Stars.route) {
                RepositoriesScreen(
                    viewModel = sharedViewModel, 
                    listType = RepoListType.STARRED,
                    title = "My Stars"
                )
            }
}
        }
    }
}