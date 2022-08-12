package com.dicoding.tourismapp.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.dicoding.tourismapp.core.domain.usecase.TourismUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

class MapsViewModel (tourismUseCase: TourismUseCase): ViewModel(){
    val tourism = tourismUseCase.getAllTourism().asLiveData()
}