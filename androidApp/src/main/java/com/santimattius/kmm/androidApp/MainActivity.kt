package com.santimattius.kmm.androidApp

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.santimattius.kmm.androidApp.databinding.ActivityMainBinding
import com.santimattius.kmm.shared.Greeting
import com.santimattius.kmm.shared.SpaceXSDK
import com.santimattius.kmm.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

fun greet(): String {
    return Greeting().greeting()
}

class MainActivity : AppCompatActivity() {

    private val mainScope = MainScope()
    private lateinit var viewBinding: ActivityMainBinding

    private val sdk = SpaceXSDK(DatabaseDriverFactory(this))
    private val launchesRvAdapter = LaunchesRvAdapter(listOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "SpaceX Launches"
        viewBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        viewBinding.launchesListRv.adapter = launchesRvAdapter
        viewBinding.launchesListRv.layoutManager = LinearLayoutManager(this)

        viewBinding.swipeContainer.setOnRefreshListener {
            viewBinding.swipeContainer.isRefreshing = false
            displayLaunches(true)
        }

        displayLaunches(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        mainScope.cancel()
    }

    private fun displayLaunches(needReload: Boolean) {
        viewBinding.progressBar.isVisible = true
        mainScope.launch {
            runCatching {
                sdk.getLaunches(needReload)
            }.onSuccess {
                launchesRvAdapter.launches = it
                launchesRvAdapter.notifyDataSetChanged()
            }.onFailure {
                Toast.makeText(this@MainActivity, it.localizedMessage, Toast.LENGTH_SHORT)
                    .show()
            }
            viewBinding.progressBar.isVisible = false
        }
    }
}

