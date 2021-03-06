package com.facundojaton.tvmazechallenge.ui.series.seriesdetails

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.adapters.DaysListAdapter
import com.facundojaton.tvmazechallenge.adapters.GenresListAdapter
import com.facundojaton.tvmazechallenge.adapters.SeasonsListAdapter
import com.facundojaton.tvmazechallenge.databinding.FragmentSeriesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSeriesDetailsBinding
    private var seasonsListAdapter = SeasonsListAdapter()

    private val seriesDetailsViewModel: SeriesDetailsViewModel by lazy {
        ViewModelProvider(this).get(SeriesDetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_series_details, container, false)
        val args = requireArguments()
        seriesDetailsViewModel.setSeries(SeriesDetailsFragmentArgs.fromBundle(args).selectedSeries)
        binding.viewModel = seriesDetailsViewModel
        binding.lifecycleOwner = this

        binding.apply {
            rvSeriesSeasons.adapter = seasonsListAdapter
            rvGenres.adapter = GenresListAdapter()
            rvSeriesDays.adapter = DaysListAdapter()
            seasonsListAdapter.onSeasonEpisodeClicked = { episode ->
                findNavController().navigate(
                    SeriesDetailsFragmentDirections.actionShowEpisode(
                        episode
                    )
                )
            }
        }

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        seriesDetailsViewModel.checkEmptyFields()
        if (seriesDetailsViewModel.episodesListEmpty) showEmptyEpisodes()
        else showEpisodes()

    }

    private fun showEpisodes() {
        binding.apply {
            clEmptyEpisodes.visibility = View.GONE
            rvSeriesSeasons.visibility = View.VISIBLE
        }
    }

    private fun showEmptyEpisodes() {
        binding.clEmptyEpisodes.visibility = View.VISIBLE
        binding.rvSeriesSeasons.visibility = View.GONE
    }
}