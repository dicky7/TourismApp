package com.dicoding.tourismapp.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.maps.databinding.ActivityMapsBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

class MapsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMapsBinding

    @Inject
    lateinit var factory: ViewModelFactory
    private val mapsViewModel: MapsViewModel by viewModels {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerMapsComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    MapsModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)

        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = "Tourism Map"
        getTourismData()

    }

    private fun getTourismData(){
        mapsViewModel.tourism.observe(this){result->
            if (result != null){
                when(result){
                    is Resource.Loading->{
                        binding.progressBar.visibility = View.VISIBLE
                    }
                    is Resource.Success->{
                        binding.progressBar.visibility = View.GONE
                        binding.tvMaps.text = "This is map of ${result.data?.get(0)?.name}"
                    }
                    is Resource.Error->{
                        binding.progressBar.visibility = View.GONE
                        binding.tvError.visibility = View.VISIBLE
                        binding.tvError.text = result.message
                    }
                }
            }
        }
    }
}