package com.kale.wfalldemo;

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


/**
 * @author Jack Tony
 * @date 2015/4/6
 */
public class DemoActivity extends AppCompatActivity {

    private final String TAG = getClass().getSimpleName();

    private float mHeaderHeight;

    private DemoFragment mDemoFragment;
    
    private Toolbar mToolbar;

    private int mNum = 0;

    private ImageView mFloatIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_activity);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mDemoFragment = new DemoFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.waterFall_fl, mDemoFragment).commit();
        mFloatIV = (ImageView) findViewById(R.id.float_imageButton);

        setViews();
    }

    protected void setViews() {
        setToolbar();
        mFloatIV.setOnClickListener(new View.OnClickListener() {
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
        //setSupportActionBar(mToolbar);
        mToolbar.inflateMenu(R.menu.aaa_menu_main);
        mToolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_launcher));//设置导航按钮
        mToolbar.setTitle("Saber");
        mToolbar.setAlpha(0);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDemoFragment.scrollToPos(0, true);
            }
        });

        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                int id = menuItem.getItemId();
                if (id == R.id.action_add_new_item) {
                    mNum++;
                    // 数据全部交给适配器进行管理
                    PhotoData photo = new PhotoData("我是第" + mNum + "个新来的");
                    mDemoFragment.insert2Top(photo);
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
         * mToolbar.getBackground();
         * mToolbar.getNavigationIcon()
         */
        /*private Drawable toolbarBgDrawable;
        private Drawable toolbarNavigationIcon;*/
    }
    
    //————————————————————————————————————————————————————————————————
    //                            Jack Tony
    //
    //                    callback start 2015/11/21  
    //                             
    //————————————————————————————————————————————————————————————————

    public void initHeight(int height) {
        mHeaderHeight = height;
    }

    public void onItemClick(int position) {
        Toast.makeText(DemoActivity.this, "on click", Toast.LENGTH_SHORT).show();
        mDemoFragment.remove(position);
    }

    public void onScrollUp() {
        // 滑动时隐藏float button
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) mFloatIV.getLayoutParams();
        int fabBottomMargin = lp.bottomMargin;
        mFloatIV.animate()
                .translationY(mFloatIV.getHeight() + fabBottomMargin)
                .setInterpolator(new AccelerateInterpolator(2)).start();
    }

    public void onScrollDown() {
        // 滑动时出现float button
        mFloatIV.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
    }

    public void onBottom() {
        Toast.makeText(DemoActivity.this, "bottom", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "on bottom");
    }

    float ratio;

    public void onMoved(int distanceX, int distanceY) {
        // Log.d(TAG, "distance X = " + distanceX + "distance Y = " + distanceY);
        ratio = Math.min(distanceY / mHeaderHeight, 1);
        mToolbar.setAlpha(ratio * 1);
    }

}
