package com.facundojaton.tvmazechallenge.ui.series

import android.os.Bundle
import android.view.*
import android.widget.AbsListView
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facundojaton.tvmazechallenge.R
import com.facundojaton.tvmazechallenge.SeriesListStatus
import com.facundojaton.tvmazechallenge.adapters.SeriesListAdapter
import com.facundojaton.tvmazechallenge.databinding.FragmentSeriesListBinding
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class SeriesListFragment : Fragment() {

    private val seriesListViewModel: SeriesListViewModel by lazy {
        ViewModelProvider(this).get(SeriesListViewModel::class.java)
    }
    private lateinit var binding: FragmentSeriesListBinding
    private var listAdapter = SeriesListAdapter()

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
               // seriesListViewModel.getShow(it)
            }
            etSeriesSearch.doOnTextChanged { text, _, _, _ ->
               seriesListViewModel.getSeriesListOnSearch(text.toString().toLowerCase(Locale.ROOT))
            }
            btnRefresh.setOnClickListener {
                //seriesListViewModel.sortSeries()
            }
        }

        seriesListViewModel.status.observe(viewLifecycleOwner, { status ->
            when (status) {
                SeriesListStatus.ERROR -> {
                    listAdapter.waiting = false
                    Toast.makeText(
                        context?.applicationContext,
                        R.string.error_fetching_series,
                        Toast.LENGTH_SHORT
                    ).show()
                }
                SeriesListStatus.LOADING -> {
                    listAdapter.waiting = true
                }
                else -> {
                    listAdapter.waiting = false
                }
            }
        })

        return binding.root
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
/*
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        moviesListViewModel.sortSeries(
            when (item.itemId) {
                R.id.most_popular_menu -> MovieProperties.POPULARITY_DESC
                R.id.a_to_z_menu -> MovieProperties.TITLE_ASC
                R.id.z_to_a_menu -> MovieProperties.TITLE_DESC
                R.id.least_popular_menu -> MovieProperties.POPULARITY_ASC
                R.id.least_recent_menu -> MovieProperties.DATE_ASC
                else -> MovieProperties.DATE_DESC
            }
        )
        if (item.itemId == android.R.id.home) {
            SessionController.apiKey = ""
            requireActivity().onBackPressed()
        }
        return true
    }
    */
}