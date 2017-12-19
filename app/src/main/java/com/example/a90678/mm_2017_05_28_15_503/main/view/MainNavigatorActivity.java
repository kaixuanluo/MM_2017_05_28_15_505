package com.example.a90678.mm_2017_05_28_15_503.main.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.KeyEvent;

import com.example.a90678.lkx_common_17_05_17_16_45.common.utils.ViewUtil;
import com.example.a90678.lkx_common_17_05_17_16_45.common.view.activity.BaseNavigatorActivity;
import com.example.a90678.mm_2017_05_28_15_503.R;


public class MainNavigatorActivity extends BaseNavigatorActivity {

    @Override
    public Fragment getFragment() {
        return MainFragment.newInstance();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);//禁止滑动

//        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
//        navigationView.setNavigationItemSelectedListener(this);

//        ImageView ivBg = (ImageView) findViewById(R.id.nav_bg_iv);
//        ivBg.setImageResource(R.drawable.bg);

        ViewUtil.setFragment(this, R.id.main_nav_fl, MainNavThemeFragment.newInstance());


    }



    public void recreateSetTheme() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
//        ViewUtil.recreateActivityCompat(this);
        recreate();
    }

    // 按下返回键时，实现HOME键的效果
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            //模拟HOME效果
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main2, menu);
//        return super.onCreateOptionsMenu(menu);
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_set:
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
