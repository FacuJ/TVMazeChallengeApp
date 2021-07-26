package com.facundojaton.tvmazechallenge.remote

object APIConstants {
    const val BASE_URL = "https://api.tvmaze.com/"

    object Endpoints {
        const val SHOWS = "shows"
        const val SEARCH_SHOWS = "search/shows"
        const val EPISODES = "episodes"
    }

    object QueryParams {
        const val PAGE = "page"
        const val NAME = "q"
    }
}