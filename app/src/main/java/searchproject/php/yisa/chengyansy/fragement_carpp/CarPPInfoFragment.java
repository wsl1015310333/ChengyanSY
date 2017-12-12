package searchproject.php.yisa.chengyansy.fragement_carpp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import searchproject.php.yisa.chengyansy.Model_All.ACarACrime;
import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/12/3 0003.
 */


public class CarPPInfoFragment extends Fragment {
    private TextView name;
    private TextView num;
    private TextView carType;
    private TextView carNum;
    private TextView plate;
    private TextView numType;
    private TextView acapInfo_plateType;
    ACarACrime aCarACrime;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= View.inflate(getActivity(), R.layout.fragment_carppinfo,null);
        aCarACrime= (ACarACrime) getArguments().getSerializable("aCarACrime");
        Log.e("aCarACrime",aCarACrime.getNumCard());
        initData();
        return view;
    }
    private void initData(){
        name=view.findViewById(R.id.acapInfo_name);
        num=view.findViewById(R.id.acapInfo_num);
        carType=view.findViewById(R.id.acapInfo_carType);
        carNum=view.findViewById(R.id.acapInfo_carNum);
        plate=view.findViewById(R.id.acapInfo_plate);
        numType=view.findViewById(R.id.acapInfo_numType);
        acapInfo_plateType=view.findViewById(R.id.acapInfo_plateType);
    }
}
