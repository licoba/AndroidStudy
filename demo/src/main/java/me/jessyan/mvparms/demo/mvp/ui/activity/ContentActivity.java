package me.jessyan.mvparms.demo.mvp.ui.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.request.target.Target;
import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import butterknife.BindView;
import io.noties.markwon.Markwon;
import io.noties.markwon.image.AsyncDrawable;
import io.noties.markwon.image.glide.GlideImagesPlugin;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.Article;

public class ContentActivity extends BaseActivity {


    @BindView(R.id.markdownView)
    TextView mMarkdownView;
    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolbar)
    Toolbar mToolbar;
    private Article article;
    Context context;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_content);
//    }

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_content;
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        context = this;
        article = (Article)(getIntent().getSerializableExtra("article"));
//        Log.e(TAG,article.toString());
//        mToolbar.setTitle(article.getTitle());
        mTitle.setText(article.getTitle());
        final Markwon markwon = Markwon.builder(context)
                // automatically create Glide instance
                .usePlugin(GlideImagesPlugin.create(context))
                // use supplied Glide instance
                .usePlugin(GlideImagesPlugin.create(Glide.with(context)))
                // if you need more control
                .usePlugin(GlideImagesPlugin.create(new GlideImagesPlugin.GlideStore() {
                    @NonNull
                    @Override
                    public RequestBuilder<Drawable> load(@NonNull AsyncDrawable drawable) {
                        return Glide.with(context).load(drawable.getDestination());
                    }

                    @Override
                    public void cancel(@NonNull Target<?> target) {
                        Glide.with(context).clear(target);
                    }
                }))
                .build();
        String markdownText = ReadFileContent(article.getTitle()+".md");
//        Log.e(TAG,markdownText);
        markwon.setMarkdown(mMarkdownView,markdownText);
    }


    /**
     *****************************************************
     * 读取文本文件中的内容
     * @方法名称: ReadTxtFile
     * @创建时间: 2014-7-17 上午11:27:13
     * @参数列表： @param strFilePath  文件名(helloworld.txt)
     *****************************************************
     */
    public  String ReadFileContent(String strFilePath) {
        AssetManager am = this.getAssets();
        String path = strFilePath;
        String content = ""; // 文件内容字符串
        // 打开文件
        File file = new File(path);
        // 如果path是传递过来的参数，可以做一个非目录的判断
        if (file.isDirectory()) {
            Log.e("TestFile", "The File is a directory.");
        } else {
            try {
                InputStream instream = am.open(strFilePath);
                if (instream != null) {
                    InputStreamReader inputreader = new InputStreamReader(
                            instream);
                    BufferedReader buffreader = new BufferedReader(inputreader);
                    String line;
                    // 分行读取
                    while ((line = buffreader.readLine()) != null) {
                        content += line + "\n";
                    }
                    instream.close();
                }
            } catch (java.io.FileNotFoundException e) {
                Log.e("TestFile", "The File doesn't not exist.");
            } catch (IOException e) {
                Log.e("TestFile IOException", e.getMessage());
            }
        }
        return content;
    }
}
