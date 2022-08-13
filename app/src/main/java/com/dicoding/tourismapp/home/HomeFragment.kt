package com.dicoding.tourismapp.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.tourismapp.R
import com.dicoding.tourismapp.core.data.Resource
import com.dicoding.tourismapp.core.ui.TourismAdapter
import com.dicoding.tourismapp.databinding.FragmentHomeBinding
import com.dicoding.tourismapp.detail.DetailTourismActivity
import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

//    hapus kode berikut
//    @Inject
//    lateinit var factory: ViewModelFactory

    private val homeViewModel: HomeViewModel by viewModels()

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

//    hapus kode berikut
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        (requireActivity().application as MyApplication).appComponent.inject(this)
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {

//            hapus kode berikut
//            val factory = ViewModelFactory.getInstance(requireActivity())
//            val viewModel = ViewModelProvider(this, factory)[HomeViewModel::class.java]

            val tourismAdapter = TourismAdapter()
            tourismAdapter.onItemClick = { selectedData ->
                val intent = Intent(activity, DetailTourismActivity::class.java)
                intent.putExtra(DetailTourismActivity.EXTRA_DATA, selectedData)
                startActivity(intent)
            }

            homeViewModel.tourism.observe(viewLifecycleOwner) { tourism ->
                if (tourism != null) {
                    when (tourism) {
                        is Resource.Loading -> binding.progressBar.visibility =
                            View.VISIBLE
                        is Resource.Success -> {
                            binding.progressBar.visibility = View.GONE
                            tourismAdapter.setData(tourism.data)
                        }
                        is Resource.Error -> {
                            binding.progressBar.visibility = View.GONE
                            binding.viewError.root.visibility = View.VISIBLE
                            binding.viewError.tvError.text =
                                tourism.message ?: getString(R.string.something_wrong)
                        }
                    }
                }
            }

            with(binding.rvTourism) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = tourismAdapter
            }

            //for test Crashlytics firebase
            binding.btnCrashlytics.setOnClickListener {
                FirebaseCrashlytics.getInstance().log("Clicked on button")
                FirebaseCrashlytics.getInstance().setCustomKey("str_key", "some_data")
                throw RuntimeException("Crashlytics Test")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}