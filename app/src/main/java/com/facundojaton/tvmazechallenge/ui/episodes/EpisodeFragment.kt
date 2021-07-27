package com.facundojaton.tvmazechallenge.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.databinding.FragmentEpisodeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EpisodeFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeBinding

    private val episodeViewModel: EpisodeViewModel by lazy {
        ViewModelProvider(this).get(EpisodeViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_episode, container, false)
        val args = requireArguments()
        episodeViewModel.setEpisode(EpisodeFragmentArgs.fromBundle(args).episode)

        binding.viewModel = episodeViewModel
        binding.lifecycleOwner = this

        return binding.root
    }
}