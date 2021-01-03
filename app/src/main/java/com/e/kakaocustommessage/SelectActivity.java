package com.e.kakaocustommessage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.e.kakaocustommessage.Adapter.OnBoardViewPagerAdapter;
import com.e.kakaocustommessage.Adapter.SelectViewPagerAdapter;
import com.e.kakaocustommessage.CreateFeed.CreateFeedActivity;
import com.e.kakaocustommessage.CreateText.CreateTextActivity;

public class SelectActivity extends AppCompatActivity {

    private ViewPager viewPager;
    private Button btnLeft, btnRight, select;
    private ImageView selectBack;
    private SelectViewPagerAdapter adapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;

      int button1select=0;
    static int button2select=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);
        init();
    }

    private void init() {
        viewPager = findViewById(R.id.view_pager);
        btnLeft = findViewById(R.id.btnLeft);
        select = findViewById(R.id.selectBtn);
        selectBack = findViewById(R.id.selectBackBtn);
        btnRight = findViewById(R.id.btnRight);
        dotsLayout = findViewById(R.id.dotsLayout);
        adapter = new SelectViewPagerAdapter(this);
        addDots(0);
        viewPager.addOnPageChangeListener(listener); // 리스너 연결
        viewPager.setAdapter(adapter);

        btnRight.setOnClickListener(v -> { viewPager.setCurrentItem(viewPager.getCurrentItem() + 1); });
        btnLeft.setOnClickListener(v -> { viewPager.setCurrentItem(viewPager.getCurrentItem() - 1); });

        select.setOnClickListener(v->{
            Intent intent = (Intent)null;
            if(viewPager.getCurrentItem()==0) intent = new Intent(this, CreateTextActivity.class);
            else if(viewPager.getCurrentItem()==1) intent = new Intent(this, CreateFeedActivity.class);
            else if(viewPager.getCurrentItem()==2) intent = new Intent(this, CreateFeedActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
        });

        selectBack.setOnClickListener(v->{
            finish();
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    //뷰페이저 점 추가
    private void addDots(int position) {
        dotsLayout.removeAllViews();
        dots = new TextView[3];
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            //점 생성하는 html 코드
            dots[i].setText(Html.fromHtml("&#8226"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.colorLightGrey));
            dotsLayout.addView(dots[i]);
        }

        // 선택하는 점 변경
        if (dots.length > 0) {
            dots[position].setTextColor(getResources().getColor(R.color.colorGrey));
        }
    }

    //뷰페이저 리스너
    private ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addDots(position);

            if (position == 0) {
                btnLeft.setVisibility(View.INVISIBLE);
                btnRight.setVisibility(View.VISIBLE);
            } else if (position == 1) {
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.VISIBLE);
            } else {
                btnLeft.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };


}