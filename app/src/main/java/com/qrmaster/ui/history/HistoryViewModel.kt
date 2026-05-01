package com.qrmaster.ui.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.qrmaster.domain.model.QRCode
import com.qrmaster.domain.model.QRCodeType
import com.qrmaster.domain.repository.QRCodeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HistoryViewModel @Inject constructor(
    private val repository: QRCodeRepository
) : ViewModel() {

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow<QRCodeType?>(null)
    val selectedFilter: StateFlow<QRCodeType?> = _selectedFilter.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val qrCodes: StateFlow<List<QRCode>> = combine(
        _searchQuery,
        _selectedFilter
    ) { query, filter ->
        Pair(query, filter)
    }.flatMapLatest { (query, filter) ->
        when {
            query.isNotBlank() -> repository.searchQRCodes(query)
            filter != null -> repository.getQRCodesByType(filter)
            else -> repository.getAllQRCodes()
        }
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        emptyList()
    )

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
    }

    fun setFilter(type: QRCodeType?) {
        _selectedFilter.value = type
    }

    fun deleteQRCode(id: Long) {
        viewModelScope.launch {
            repository.deleteQRCode(id)
        }
    }
}