package com.facundojaton.tvmazechallenge.remote

import com.facundojaton.tvmazechallenge.model.Series
import com.facundojaton.tvmazechallenge.model.SeriesResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap
import java.util.HashMap

interface SeriesService {

    @GET(APIConstants.Endpoints.SEARCH_SHOWS)
    suspend fun searchShowsByName(@Query(APIConstants.QueryParams.NAME) param: String): List<SeriesResponse>

    @GET(APIConstants.Endpoints.SHOWS)
    suspend fun getAllShows() : List<Series>

    @GET(APIConstants.Endpoints.SHOWS)
    suspend fun getShowsByParams(@QueryMap params: HashMap<String, String>): List<Series>

}