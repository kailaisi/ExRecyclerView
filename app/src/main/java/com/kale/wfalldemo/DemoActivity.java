package com.kale.wfalldemo;

import com.kale.wfalldemo.extra.net.DataManager;
import com.kale.wfalldemo.extra.net.ResponseCallback;
import com.kale.wfalldemo.mode.PhotoData;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;


/**
 * @author Jack Tony
 * @date 2015/4/6
 */
public class DemoActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private float headerHeight;

    private DemoFragment mDemoFragment;
    
    private Toolbar toolbar;

    private int num = 0;

    private ImageView floatIV;

    private DataManager mDataManager = new DataManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        mDemoFragment = new DemoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.waterFall_fl, mDemoFragment).commit();
        floatIV = (ImageView) findViewById(R.id.float_imageButton);

        setViews();
        loadData(true);
    }

    protected void setViews() {
        setToolbar();
        floatIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDemoFragment.scrollToPos(0, true);
            }
        });
    }

    /**
     * 设置toolbar的背景图和menu点击事件
     */
    private void setToolbar() {
        //setSupportActionBar(toolbar);
        toolbar.inflateMenu(R.menu.aaa_menu_main);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_launcher));//设置导航按钮
        toolbar.setTitle("Saber");
        toolbar.setAlpha(0);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDemoFragment.scrollToPos(0, true);
            }
        });

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_add_new_item) {
                    num++;
                    // 数据全部交给适配器进行管理
                    PhotoData photo = new PhotoData();
                    photo.msg = "我是第" + num + "个新来的";
                    photo.favorite_count = -5;
                    photo.photo = new PhotoData.Photo();
                    photo.photo.width = 800;
                    photo.photo.height = 1000;
                    photo.photo.path = "http://images.cnitblog.com/blog/651487/201501/292018114419518.png";

                    mDataManager.getData().add(0, photo);
                    mDemoFragment.updateData(mDataManager.getData());
                    return true;
                } else {
                    return false;
                }
            }
        });

        /**
         * 本范例让toolbar(view)整体的随着滚动而渐变，
         * 如果你仅仅想要让背景渐变可以用下面的drawable来设置透明度，
         * 它们都是drawable，透明度最大值是255
         *
         * toolbar.getBackground();
         * toolbar.getNavigationIcon()
         */
        /*private Drawable toolbarBgDrawable;
        private Drawable toolbarNavigationIcon;*/
    }

    public void loadData(boolean loadNewData) {
        //Log.d(TAG, "加载新的数据");
        ResponseCallback callback = new ResponseCallback() {
            @Override
            public void onSuccess(Object object) {
                mDemoFragment.updateData((List<PhotoData>) object);
            }

            @Override
            public void onError(String msg) {
                Toast.makeText(getBaseContext(), msg, Toast.LENGTH_SHORT).show();
            }
        };
        if (loadNewData) {
            mDataManager.loadNewData(callback);
        } else {
            mDataManager.loadOldData(callback);
        }
    }
    
    
    /// ------------------ 回调 ------------------------
    

    public void initHeight(int height) {
        headerHeight = height;
    }

    public void onItemClick(int position) {
        Toast.makeText(DemoActivity.this, "on click", Toast.LENGTH_SHORT).show();
        mDataManager.getData().remove(position);
        mDemoFragment.updateData(mDataManager.getData());
    }

    public void onScrollUp() {
        // 滑动时隐藏float button
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) floatIV.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        floatIV.animate()
                .translationY(floatIV.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public void onScrollDown() {
        // 滑动时出现float button
        floatIV.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void onBottom() {
        //Log.d(TAG, "on bottom");
        Toast.makeText(DemoActivity.this, "bottom", Toast.LENGTH_SHORT).show();
        // 到底部自动加载
        Log.d(TAG, "loading old data");
        //loadData(false);
    }

    float ratio;

    public void onMoved(int distanceX, int distanceY) {
        // Log.d(TAG, "distance X = " + distanceX + "distance Y = " + distanceY);
        ratio = Math.min(distanceY / headerHeight, 1);
        toolbar.setAlpha(ratio * 1);
    }

}
