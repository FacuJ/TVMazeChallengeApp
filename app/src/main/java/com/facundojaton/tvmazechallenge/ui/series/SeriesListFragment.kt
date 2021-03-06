package com.facundojaton.tvmazechallenge.ui.series

import android.os.Bundle
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.RequestStatus
import com.facundojaton.tvmazechallenge.adapters.SeriesListAdapter
import com.facundojaton.tvmazechallenge.databinding.FragmentSeriesListBinding
import com.facundojaton.tvmazechallenge.model.SeriesDetail
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    private val seriesListViewModel: SeriesListViewModel by lazy {
        ViewModelProvider(this).get(SeriesListViewModel::class.java)
    }
    private lateinit var binding: FragmentSeriesListBinding
    private var listAdapter = SeriesListAdapter()
    private var textChangedListener: TextWatcher? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series_list, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = seriesListViewModel
        setHasOptionsMenu(true)


        binding.apply {
            rvSeries.adapter = listAdapter
            rvSeries.addOnScrollListener(customScrollListener)
            listAdapter.onSeriesClicked = {
                seriesListViewModel.selectSeries(it)
            }
            btnRefresh.setOnClickListener {
                seriesListViewModel.refresh()
            }
        }

        seriesListViewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                RequestStatus.ERROR -> {
                    listAdapter.waiting = false
                    Toast.makeText(
                        context?.applicationContext,
                        R.string.error_fetching_series,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                RequestStatus.LOADING -> {
                    listAdapter.waiting = true
                }
                else -> {
                    listAdapter.waiting = false
                }
            }
        })

        seriesListViewModel.selectedSeries.observe(viewLifecycleOwner, { detail ->
            detail?.let {
                navigateToSeriesDetail(it)
            }
        })

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (binding.etSeriesSearch.text?.isNotBlank() == true) binding.etSeriesSearch.setText(
                        ""
                    )
                    else {
                        this.remove()
                        requireActivity().onBackPressed()
                    }
                }
            })

        return binding.root
    }

    private fun navigateToSeriesDetail(detail: SeriesDetail) {
        seriesListViewModel.navigateToDetailsFinished()
        this.findNavController().navigate(SeriesListFragmentDirections.actionShowDetail(detail))
    }


    private val customScrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)
            val layoutManager = recyclerView.layoutManager as GridLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount
            seriesListViewModel.paginateIfNeeded(
                firstVisibleItemPosition,
                visibleItemCount,
                totalItemCount
            )
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                seriesListViewModel.onScrollStateTrue()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        textChangedListener = binding.etSeriesSearch.doOnTextChanged { text, _, _, _ ->
            seriesListViewModel.getSeriesListOnSearch(text.toString().toLowerCase(Locale.ROOT))
        }
    }

    override fun onPause() {
        super.onPause()
        binding.etSeriesSearch.removeTextChangedListener(textChangedListener)
    }


}