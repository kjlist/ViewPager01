package as.laoli.com.viewpager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends FragmentActivity {
    private ViewPager_Indicator viewPager_indicator;
    private ViewPager viewPager;
    private List<String>mTitles= Arrays.asList("短信1","收藏2","推荐3","短信4","收藏5","推荐6","短信7","收藏8"
            ,"推荐9");
    private List<VpSimpleFragment>mContents=new ArrayList<VpSimpleFragment>();
    private FragmentPagerAdapter mAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initViews();
        initDatas();
        viewPager_indicator.setmVisibleCount(5);
        viewPager_indicator.setTabItemTitles(mTitles);
        viewPager.setAdapter(mAdapter);
        //不用了???
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //TabWidth*positionOffset+posion*Tabwidth
                viewPager_indicator.scroll(position,positionOffset);
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initDatas() {
        for (String title:mTitles){
            VpSimpleFragment fragment=VpSimpleFragment.newInstance(title);
            mContents.add(fragment);
        }
        mAdapter=new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mContents.get(position);
            }

            @Override
            public int getCount() {
                return mContents.size();
            }
        };
    }

    private void initViews() {
        viewPager= (ViewPager) findViewById(R.id.viewpager);
        viewPager_indicator= (ViewPager_Indicator) findViewById(R.id.indicator);
    }
}
