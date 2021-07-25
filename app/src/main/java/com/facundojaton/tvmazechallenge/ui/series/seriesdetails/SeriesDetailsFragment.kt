package com.facundojaton.tvmazechallenge.ui.series.seriesdetails

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.adapters.EpisodesListAdapter
import com.facundojaton.tvmazechallenge.databinding.FragmentSeriesDetailsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SeriesDetailsFragment : Fragment() {

    private lateinit var binding: FragmentSeriesDetailsBinding
    private var episodesListAdapter = EpisodesListAdapter()

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
            rvSeriesEpisodes.adapter = EpisodesListAdapter()
            //rvSeriesCast.adapter = castListAdapter
        }
        /*castListAdapter.onCharacterClicked = {
            showActorDialog(it)
        }*/
        //Handle Error?

        return binding.root
    }

   /* private fun showActorDialog(character: CastCharacter) {
        context?.let { fragmentContext ->
            Dialog(fragmentContext).apply {
                val dialogBinding = CustomDialogCastCharacterBinding.inflate(layoutInflater)
                requestWindowFeature(Window.FEATURE_NO_TITLE)
                setContentView(dialogBinding.root)
                window?.setLayout(
                    WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT
                )
                window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                setCanceledOnTouchOutside(true)
                dialogBinding.castCharacter = character
                show()
            }
        }
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       // if (seriesDetailsViewModel.isCastEmpty) showEmptyCast()
        if (seriesDetailsViewModel.episodesListEmpty) showEmptyEpisodes()
    }

    private fun showEmptyEpisodes() {
        binding.clEmptyEpisodes.visibility = View.VISIBLE
        binding.rvSeriesEpisodes.visibility = View.GONE
    }
}