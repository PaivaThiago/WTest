package paiva.thiago.wtest.app.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import paiva.thiago.wtest.app.repository.PostalCodeRepository

class PostalCodeViewModel(private val repository: PostalCodeRepository) : ViewModel() {

    private val _loading: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val loading = _loading.asStateFlow()

    init {
        viewModelScope.launch {
            repository.initDB()
            _loading.update { false }
        }
    }

    fun search(query: String) = repository.searchPostalCodes(query)
}