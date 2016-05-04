package as.laoli.com.viewpager;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**cmd+f12可以看到所有方法
 * Created by laoli on 16/5/3.
 */
public class VpSimpleFragment extends Fragment {
    private String mTitle;
    public static final String BUNDLE_TITLE="title";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Bundle bundle=getArguments();
        if (bundle!=null){
            mTitle=bundle.getString(BUNDLE_TITLE);
        }
        TextView textView=new TextView(getActivity());
        textView.setText(mTitle);
        textView.setGravity(Gravity.CENTER);
        return textView;
    }

    public static VpSimpleFragment newInstance(String title){
        Bundle bundle=new Bundle();
        bundle.putString(BUNDLE_TITLE,title);
        VpSimpleFragment fragment=new VpSimpleFragment();
        fragment.setArguments(bundle);
        return fragment;

    }
}
