package com.example.screenstate

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.screenstate.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applyBinding()
    }

    private fun applyBinding() {
        viewModel.homeListApiState.observe(this) {
            binding.progressCircular.visibility =
                getVisibility(isVisible = (it is ApiState.Loading) or (it is ApiState.PreLoading))
            binding.mainContent.visibility = getVisibility(isVisible = it is ApiState.Loaded)
            binding.errorScreen.root.visibility = getVisibility(isVisible = it is ApiState.Error)

            if (it is ApiState.Error) {
                it.manageToastAction(context = this)
                ErrorBinding.applyBinding(
                    binding = binding.errorScreen,
                    state = it,
                    onRetryClick = {
                        viewModel.fetchHomeList()
                    }
                )
            }
        }

        binding.mainContent.setOnClickListener { viewModel.fetchHomeList() }
    }
}

fun getVisibility(isVisible: Boolean) = if (isVisible) View.VISIBLE else View.GONE