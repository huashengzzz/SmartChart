package in.srain.cube.views.ptr.loadmore;

import android.view.View;
import android.widget.AbsListView;

public interface LoadMoreContainer {

    void setShowLoadingForFirstPage(boolean showLoading);

    void setAutoLoadMore(boolean autoLoadMore);

    void setOnScrollListener(AbsListView.OnScrollListener l);

    void setLoadMoreView(View view);

    void setLoadMoreUIHandler(LoadMoreUIHandler handler);

    void setLoadMoreHandler(LoadMoreHandler handler);

    /**
     * When data has loaded
     *
     * @param emptyResult
     * @param hasMore
     */
    void loadMoreFinish(boolean emptyResult, boolean hasMore);

    /**
     * When something unexpected happened while loading the data
     *
     * @param errorCode
     * @param errorMessage
     */
    void loadMoreError(int errorCode, String errorMessage);
}
