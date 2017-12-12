package searchproject.php.yisa.chengyansy.fragement_carpp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/12/3 0003.
 */

public class CarPPCrimeFragment extends Fragment {
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= View.inflate(getActivity(), R.layout.fragment_carppcrime,null);
        return view;
    }
}
