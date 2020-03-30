package me.jessyan.mvparms.demo.mvp.presenter;

import android.app.Application;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.mvp.BasePresenter;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;

@ActivityScope
public class MainPresenter extends BasePresenter<UserContract.Model, UserContract.View> {
    @Inject
    RxErrorHandler mErrorHandler;
    @Inject
    AppManager mAppManager;
    @Inject
    Application mApplication;
}
