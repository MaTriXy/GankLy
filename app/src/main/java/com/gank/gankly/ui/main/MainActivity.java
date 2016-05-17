package com.gank.gankly.ui.main;

import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.gank.gankly.R;
import com.gank.gankly.ui.base.BaseActivity;
import com.gank.gankly.ui.collect.CollectFragment;
import com.gank.gankly.ui.main.meizi.GiftFragment;
import com.gank.gankly.ui.main.video.VideoFragment;
import com.gank.gankly.utils.AppUtils;
import com.gank.gankly.utils.ToastUtils;

import butterknife.Bind;

public class MainActivity extends BaseActivity {
    @Bind(R.id.main_navigation)
    NavigationView mNavigationView;
    @Bind(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    private long mKeyTime;
    private Fragment mCurFragment;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
                mDrawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
            if ((System.currentTimeMillis() - mKeyTime) > 2000) {
                mKeyTime = System.currentTimeMillis();
                ToastUtils.shortBottom(R.string.app_again_out);
                return false;
            } else {
                AppUtils.killProcess();
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void bindListener() {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                mDrawerLayout.closeDrawers();
                Fragment fragmentTo = null;
                switch (menuItem.getItemId()) {
                    case R.id.navigation_collect:
                        fragmentTo = CollectFragment.newInstance();
                        break;
                    case R.id.navigation_video:
                        fragmentTo = VideoFragment.getInstance();
                        break;
                    case R.id.navigation_home:
                        fragmentTo = MainFragment.getInstance();
                        break;
                    case R.id.navigation_about:
                        fragmentTo = AboutFragment.getInstance();
                        break;
                    case R.id.navigation_gift:
                        fragmentTo = GiftFragment.getInstance();
                        break;
                    default:
                        break;
                }
                switchFragment(fragmentTo);
                return true;
            }
        });
    }

    private void switchFragment(Fragment fragment) {
        if (fragment == null) {
            return;
        }
        if (!fragment.getClass().getName().equals(mCurFragment.getClass().getName())) {
            addHideFragment(mCurFragment, fragment);
            mCurFragment = fragment;
        }
    }

    @Override
    protected void initValues() {
        mCurFragment = MainFragment.getInstance();
        add(mCurFragment);
    }

    public void openDrawer() {
        mDrawerLayout.openDrawer(GravityCompat.START);
    }
}
