/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.jessyan.mvparms.demo.mvp.model;

import android.content.res.AssetManager;
import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.jess.arms.base.BaseApplication;
import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.integration.AppManager;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.functions.Function;
import io.rx_cache2.DynamicKey;
import io.rx_cache2.EvictDynamicKey;
import me.jessyan.mvparms.demo.mvp.contract.MainContract;
import me.jessyan.mvparms.demo.mvp.contract.UserContract;
import me.jessyan.mvparms.demo.mvp.model.api.cache.CommonCache;
import me.jessyan.mvparms.demo.mvp.model.api.service.UserService;
import me.jessyan.mvparms.demo.mvp.model.entity.Article;
import me.jessyan.mvparms.demo.mvp.model.entity.User;
import timber.log.Timber;

import static androidx.constraintlayout.widget.Constraints.TAG;

/**
 * ================================================
 * 展示 Model 的用法
 *
 * @see <a href="https://github.com/JessYanCoding/MVPArms/wiki#2.4.3">Model wiki 官方文档</a>
 * Created by JessYan on 09/04/2016 10:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
@ActivityScope
public class MainModel extends BaseModel implements MainContract.Model {
    public static final int USERS_PER_PAGE = 10;

    @Inject
    public MainModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    AssetManager assetManager;

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    void onPause() {
        Timber.d("Release Resource");
    }

    @Override
    public Observable<List<Article>> getArticles() {
        List<Article> articles = new ArrayList<>();


        String[] files = null;
        try {// 遍历assest文件夹
            files = AppManager.getAppManager().getTopActivity().getAssets().list("");
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < files.length; i++) {
            String fileName = files[i];
            if(fileName.endsWith(".md")) {
                String filePath = "file:///android_asset/"+fileName;
                Article article = new Article(fileName.split(".md")[0],"20200331000000",filePath);
                articles.add(article);
                Log.e("fileName", " fileName: " + fileName);
            }
        }

        Observable<List<Article>> result = Observable.create(new ObservableOnSubscribe<List<Article>>() {
            @Override
            public void subscribe(ObservableEmitter<List<Article>> emitter) throws Exception {
                emitter.onNext(articles);
                emitter.onComplete();
            }
        });
        return result;
    }
}
