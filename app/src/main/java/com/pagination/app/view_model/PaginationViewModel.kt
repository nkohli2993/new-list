package com.pagination.app.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pagination.app.base.BaseViewModel
import com.pagination.app.data.APIDataBean
import com.pagination.app.network.retrofit.DataResult
import com.pagination.app.network.retrofit.Event
import com.pagination.app.repository.PaginationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class PaginationViewModel  @Inject constructor(
    private val pageRepository: PaginationRepository,
) : BaseViewModel() {

    private var _dataResponseLiveData =
        MutableLiveData<Event<DataResult<ArrayList<APIDataBean>>>>()


    var dataResponseLiveData: LiveData<Event<DataResult<ArrayList<APIDataBean>>>> =
        _dataResponseLiveData


    fun getDataList() {
        viewModelScope.launch {
            val response =
                pageRepository.getDataList()
            withContext(Dispatchers.Main) {
                response.collect { _dataResponseLiveData.postValue(Event(it)) }
            }
        }
    }
  }