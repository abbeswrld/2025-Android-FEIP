package co.feip.fefu2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import co.feip.fefu2025.data.repository.RepositoryImpl
import co.feip.fefu2025.data.source.local.AppDatabase
import co.feip.fefu2025.navigation.AppNavHost
import co.feip.fefu2025.presentation.list.RepositoriesViewModel
import co.feip.fefu2025.ui.theme.FEFU2025AndroidBaseRepoTheme

class MainActivity : ComponentActivity() {

    private val database by lazy { AppDatabase.getDatabase(this) }

    private val repository by lazy { RepositoryImpl(database.repositoryDao()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FEFU2025AndroidBaseRepoTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "FEIP",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
                val viewModelFactory = RepositoriesViewModelFactory(repository)
                AppNavHost(viewModelFactory = viewModelFactory)

            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FEFU2025AndroidBaseRepoTheme {
        Greeting("Android")

class RepositoriesViewModelFactory(
    private val repository: RepositoryImpl
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RepositoriesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RepositoriesViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}