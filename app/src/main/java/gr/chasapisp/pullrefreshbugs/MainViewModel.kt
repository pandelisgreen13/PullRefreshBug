package gr.chasapisp.pullrefreshbugs

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


class MainViewModel : ViewModel() {

    private val mutableUiFlow = MutableStateFlow(UiState())
    val uiFlow = mutableUiFlow.asStateFlow()

    fun fetch() {
        viewModelScope.launch {
            mutableUiFlow.update {
                it.copy(isLoading = true)
            }
       //     delay(100)
            mutableUiFlow.update {
                it.copy(isLoading = false,
                    items = list()
                )
            }

        }
    }
}

private fun list() = List(size = 100) {
    "1"
}

data class UiState(
    val isLoading: Boolean = false,
    val items: List<String> = emptyList()
)