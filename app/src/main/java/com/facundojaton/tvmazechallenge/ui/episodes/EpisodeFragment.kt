package com.facundojaton.tvmazechallenge.ui.episodes

import android.os.Bundle
import android.view.*
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.adapters.EpisodesListAdapter
import com.facundojaton.tvmazechallenge.databinding.FragmentEpisodeBinding
import com.facundojaton.tvmazechallenge.databinding.FragmentSeriesDetailsBinding
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
}