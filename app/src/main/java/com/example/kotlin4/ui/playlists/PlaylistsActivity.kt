package com.example.kotlin4.ui.playlists

import android.content.Intent
import android.view.LayoutInflater
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.kotlin4.base.BaseActivity
import com.example.kotlin4.chekNetwork.ConnectionLiveData
import com.example.kotlin4.databinding.ActivityPlaylistsBinding
import com.example.kotlin4.model.Item

class PlaylistsActivity : BaseActivity<PlaylistViewModel, ActivityPlaylistsBinding>() {

    private val adapter = PlaylistAdapter(this::clickItem)

    private fun clickItem(id: String) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("key", id)
        startActivity(intent)
    }

    private lateinit var chekNetwork: ConnectionLiveData
    override val viewModel: PlaylistViewModel by lazy {
        ViewModelProvider(this)[PlaylistViewModel::class.java]
    }

    override fun initView() {
        binding.recyclerPlaylist.layoutManager = LinearLayoutManager(this)
        binding.recyclerPlaylist.adapter = adapter
    }

    override fun initViewModel() {
        viewModel.getPlaylists().observe(this) {
            adapter.setList(it.items as ArrayList<Item>)
        }
    }//END

    override fun inflateViewBinding(inflater: LayoutInflater): ActivityPlaylistsBinding {
        return ActivityPlaylistsBinding.inflate(inflater)
    }

    override fun checkInternet() {
        chekNetwork = ConnectionLiveData(this)
        chekNetwork.observe(this) {
            if (it == true) {
                initViewModel()
                binding.recyclerPlaylist.isVisible = true
                binding.layoutNoNetwork.root.isInvisible = true
            } else {
                binding.recyclerPlaylist.isInvisible = true
                binding.layoutNoNetwork.root.isVisible = true
            }
        }
    }
}