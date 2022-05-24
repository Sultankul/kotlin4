package com.example.kotlin4.ui.playlists

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.kotlin4.BuildConfig.API_KEY
import com.example.kotlin4.`object`.Constant
import com.example.kotlin4.base.BaseViewModel
import com.example.kotlin4.model.Playlist
import com.example.kotlin4.remote.ApiService
import com.example.kotlin4.remote.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PlaylistViewModel : BaseViewModel() {

    private val apiService: ApiService by lazy {
        RetrofitClient.create()
    }


    fun getPlaylists(): LiveData<Playlist> {
        return playlist()
    }

    private fun playlist(): LiveData<Playlist> {
        val data = MutableLiveData<Playlist>()

        apiService.getPlaylists(Constant.part, API_KEY, Constant.channelId, 50).enqueue(object :
            Callback<Playlist> {
            override fun onResponse(call: Call<Playlist>, response: Response<Playlist>) {
                if (response.isSuccessful) {
                    data.value = response.body()
                }
            }

            override fun onFailure(call: Call<Playlist>, t: Throwable) {

                // 404 - не найдено 401 - токен истек, 403 - нет доступа
                print(t.stackTrace)
            }
        })

        return data
    }
}