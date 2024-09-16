@file:OptIn(ExperimentalMaterial3Api::class)

package gr.chasapisp.pullrefreshbugs

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import gr.chasapisp.pullrefreshbugs.ui.theme.PullRefreshBugsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PullRefreshBugsTheme {
                val viewModel = MainViewModel()

                viewModel.fetch()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->


                    val uiState by viewModel.uiFlow.collectAsStateWithLifecycle()

                    LazyCoL(
                        modifier = Modifier.padding(innerPadding),
                        uiState
                    ) {
                        viewModel.fetch()
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LazyCoL(
    modifier: Modifier = Modifier, uiState: UiState,
    refresh: () -> Unit = {}
) {

    PullToRefreshBox(
        isRefreshing = uiState.isLoading,
        onRefresh = {
            refresh.invoke()
        },
        modifier = modifier
    ) {

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
        ) {
            items(uiState.items.size) {
                Text(
                    text = "Hello android!"
                )
            }
        }
    }


}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PullRefreshBugsTheme {
        LazyCoL(uiState = UiState())
    }
}