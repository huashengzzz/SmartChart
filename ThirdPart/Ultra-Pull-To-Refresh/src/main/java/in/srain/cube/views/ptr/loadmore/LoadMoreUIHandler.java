package in.srain.cube.views.ptr.loadmore;

public interface LoadMoreUIHandler {

    void onLoading(LoadMoreContainer container);

    void onLoadFinish(LoadMoreContainer container, boolean empty, boolean hasMore);

    void onWaitToLoadMore(LoadMoreContainer container);

    void onLoadError(LoadMoreContainer container, int errorCode, String errorMessage);
}