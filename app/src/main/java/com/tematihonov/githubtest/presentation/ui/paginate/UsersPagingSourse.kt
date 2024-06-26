package com.tematihonov.githubtest.presentation.ui.paginate

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.tematihonov.githubtest.domain.models.responseSearch.Item

typealias UsersPageLoader = suspend (pageIndex: Int, pageSize: Int) -> List<Item>

class UsersPagingSource(
    private val loader: UsersPageLoader,
    private val pageSize: Int,
) : PagingSource<Int, Item>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Item> {
        val pageIndex = params.key ?: 0

        return try {
            val users = loader.invoke(pageIndex, params.loadSize)

            return LoadResult.Page(
                data = users,
                prevKey = if (pageIndex == 0) null else pageIndex - 1,
                nextKey = if (users.size == params.loadSize) pageIndex + (params.loadSize / pageSize) else null
            )
        } catch (e: Exception) {
            LoadResult.Error(
                throwable = e
            )
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Item>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}
