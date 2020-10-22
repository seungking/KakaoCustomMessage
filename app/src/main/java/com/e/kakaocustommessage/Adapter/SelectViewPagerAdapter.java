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

public class SelectViewPagerAdapter extends PagerAdapter {

    private Context context;
    private LayoutInflater inflater;

    public SelectViewPagerAdapter(Context context) {
        this.context = context;
    }

    //설정 값들 선언
    private int images[] ={
            R.drawable.logo,
            R.drawable.logo2,
            R.drawable.logo
    };

    private String titles[] ={
            "주의사항",
            "사용 방법1",
            "사용 방법2"
    };

    private String descs[] ={
            "앱의 절전 모드를 꺼주세요. 정상 작동이 불가능할 수 있습니다. \n 앱을 완전히 끄지 말아주세요. 서비스 이용에 제한됩니다. :(",
            "오른쪽 상단의 메세지 추가 아이콘을 클릭하고 친구 목록 중 보낼 친구를 선택해주세요 :)",
            "예약할 날짜, 시간을 선택하고 예약 메세지를 입력해주세요 :)"
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
