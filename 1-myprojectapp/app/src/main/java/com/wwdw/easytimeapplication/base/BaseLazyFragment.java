package com.wwdw.easytimeapplication.base;

import android.os.Handler;
import android.view.View;

public abstract class BaseLazyFragment extends BaseFragment {

    //Fragment的View加载完毕的标记
    private boolean isViewCreated;
    //Fragment对用户可见的标记
    private boolean isUIVisible;

    @Override
    public int getLayOutId() {
        return getLayOutIdLazy();
    }

    @Override
    public void initView(View view) {
        initViewLazy(view);
        isViewCreated = true;
        initObjLzay();
        lazyLoad();
    }

    @Override
    public void initData() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        //isVisibleToUser这个boolean值表示:该Fragment的UI 用户是否可见
        if (isVisibleToUser) {
            isUIVisible = true;
            lazyLoad();
        } else {
            isUIVisible = false;
        }
    }

    private void lazyLoad() {
        //这里进行双重标记判断,是因为setUserVisibleHint会多次回调,并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
        if (isViewCreated && isUIVisible) {
            initDataLazy();
            initListenerLazy();
            //数据加载完毕,恢复标记,防止重复加载
            isViewCreated = false;
            isUIVisible = false;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        //页面销毁,恢复标记
        isViewCreated = false;
        isUIVisible = false;
    }

    public abstract int getLayOutIdLazy();

    public abstract void initViewLazy(View view);

    public abstract void initObjLzay();

    public abstract void initDataLazy();

    public abstract void initListenerLazy();

    public Handler getHandlerLazy() {
        return getHandlers();
    }

}
