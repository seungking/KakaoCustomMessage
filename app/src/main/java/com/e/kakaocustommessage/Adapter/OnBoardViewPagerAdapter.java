package com.e.kakaocustommessage.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import com.e.kakaocustommessage.R;

public class OnBoardViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    public OnBoardViewPagerAdapter(Context context) {
        this.context = context;
    }

    //설정 값들 선언
    private int images[] ={
            R.drawable.coach1,
            R.drawable.coach3,
            R.drawable.coach2,
            R.drawable.logotxt
    };

    private String titles[] ={
            "커스텀 메시지",
            "메시지 설정",
            "나에게 보내기",
            ""
    };

    private String descs[] ={
            "피드 메시지, 텍스트 메시지를 이용하여\n나만의 카카오톡 메시지를 만들어보세요.\n",
            "메시지를 직접 설정해보세요.\n링크 사이트 : 네이버, 카카오, 구글, 네이버지도, 구글지도",
            "나에게 보내는 메시지로 전송됩니다.\n메시지를 복사하여 원하는곳에 보내세요.",
            "지금 만들어보세요."
    };

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout)object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        inflater = (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.onboard_view_pager,container,false);

        ImageView imageView = v.findViewById(R.id.imgViewPager);
        TextView txtTitle = v.findViewById(R.id.txtTitleViewPager);
        TextView txtDesc = v.findViewById(R.id.txtDescViewPager);

        imageView.setImageResource(images[position]);
        txtTitle.setText(titles[position]);
        txtDesc.setText(descs[position]);

        container.addView(v);
        return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
