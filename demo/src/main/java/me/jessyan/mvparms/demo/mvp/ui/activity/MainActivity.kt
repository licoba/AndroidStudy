package me.jessyan.mvparms.demo.mvp.ui.activity

import android.app.Activity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import butterknife.BindView
import com.jess.arms.base.BaseActivity
import com.jess.arms.di.component.AppComponent
import com.tbruyelle.rxpermissions2.RxPermissions
import me.jessyan.mvparms.demo.R
import me.jessyan.mvparms.demo.mvp.contract.MainContract
import me.jessyan.mvparms.demo.mvp.presenter.MainPresenter
import timber.log.Timber
import javax.inject.Inject



//这里的Activity完全就只有View层的东西了，不参与其它的实现
class MainActivity : BaseActivity<MainPresenter>(), MainContract.View {

    //这里是butterknife 的特性，来代替findviewbyid
    @BindView(R.id.recyclerView)
    var mRecyclerView: RecyclerView? = null
    @BindView(R.id.swipeRefreshLayout)
    var mSwipeRefreshLayout: SwipeRefreshLayout? = null
    @Inject
    var mRxPermissions: RxPermissions? = null
    override fun setupActivityComponent(appComponent: AppComponent) {
        return;
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    //BaseActivity<MainPresenter>()的方法
    override fun initData(savedInstanceState: Bundle?) {
        return;
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_main;
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

    }


    // MainContract.View的方法
    override fun getActivity(): Activity {
        return this;
    }

    override fun getRxPermissions(): RxPermissions {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun startLoadMore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage(message: String) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun endLoadMore() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    // IView的写法，几乎每个页面都有loading方法，所以写进IView里面去
    override fun showLoading() {
        Timber.tag(TAG).w("showLoading")
        //kotlin的写法，！！两个感叹号代表：表示当前对象不为空的情况下执行，如果为空就报错了
        // 它和?.的区别在于：?.不会报错，会返回null
        mSwipeRefreshLayout!!.isRefreshing = true
    }

    override fun hideLoading() {
        super.hideLoading()
        Timber.tag(TAG).w("showLoading")
        mSwipeRefreshLayout!!.isRefreshing = false
    }

}
