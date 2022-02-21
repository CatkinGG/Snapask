package com.my.snapask.view.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.my.snapask.model.api.ApiResult
import com.my.snapask.model.api.vo.RepoItem
import com.my.snapask.model.api.vo.UserItem
import com.my.snapask.model.manager.RepositoryManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import retrofit2.HttpException

class HomeViewModel @ViewModelInject constructor(
    private val repositoryManager: RepositoryManager
) : ViewModel() {
    private var _selectedUserName = MutableLiveData<String>()
    val selectedUserName: LiveData<String> = _selectedUserName

    private val _getUsersResult = MutableLiveData<ApiResult<List<UserItem>>>()
    val getUsersResult: LiveData<ApiResult<List<UserItem>>> = _getUsersResult

    private val _getUserResult = MutableLiveData<ApiResult<UserItem>>()
    val getUserResult: LiveData<ApiResult<UserItem>> = _getUserResult

    private val _getUserReposResult = MutableLiveData<ApiResult<List<RepoItem>>>()
    val getUserReposResult: LiveData<ApiResult<List<RepoItem>>> = _getUserReposResult

    fun setSelectedUser(username: String){
        _selectedUserName.value = username
    }

    fun getUsers() {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getUsers()
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getUsersResult.postValue(it) }
        }
    }

    fun getUserByUserName(username: String) {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getUserByUserName(username)
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getUserResult.postValue(it) }
        }
    }

    fun getUserRepos(username: String) {
        viewModelScope.launch {
            flow {
                val resp = repositoryManager.homeRepository.getUserRepos(username)
                if (!resp.isSuccessful) throw HttpException(resp)
                emit(ApiResult.success(resp.body()))
            }
                .flowOn(Dispatchers.IO)
                .catch { e -> emit(ApiResult.error(e)) }
                .collect { _getUserReposResult.postValue(it) }
        }
    }
}