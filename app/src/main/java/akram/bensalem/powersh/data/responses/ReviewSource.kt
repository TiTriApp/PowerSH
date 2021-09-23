package akram.bensalem.powersh.data.responses

import akram.bensalem.powersh.data.model.Review
import androidx.paging.PagingSource
import androidx.paging.PagingState

/*
class ReviewSource() : PagingSource<Int, Review>() {


    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        TODO("Not yet implemented")
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val nextPage = params.key ?: 1
            val movieListResponse =      movieRepository.getPopularMovies(nextPage)

            LoadResult.Page(
                data = movieListResponse.results,
                prevKey = if (nextPage == 1) null else nextPage - 1,
                nextKey = movieListResponse.page.plus(1)
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}*/
