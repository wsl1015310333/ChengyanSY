package searchproject.php.yisa.chengyansy.fragement_carpp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import searchproject.php.yisa.chengyansy.Model_All.ACarACrime;
import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/12/3 0003.
 */

public class CarPPCrimeFragment extends Fragment {
    private TextView  acapCrim_name;
    private TextView  acapCrim_tel;
    private TextView  acapCrim_carNum;
    private TextView  acapCrim_num;
    private TextView  acapCrim_isztry;
    private TextView  acapCrim_isqkry;
    private TextView  acapCrim_iszdry;
    private TextView  acapCrim_issdry;
    private TextView  acapCrim_issary;
    private LinearLayout a_car_pepole_info_nodata;
    private LinearLayout a_car_pepole_info_data;

    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= View.inflate(getActivity(), R.layout.fragment_carppcrime,null);
        initView();
        String isNO= (String) getArguments().getSerializable("isNull");
        if(isNO.equals("yes")){
            a_car_pepole_info_nodata.setVisibility(View.VISIBLE);
            a_car_pepole_info_data.setVisibility(View.GONE);
        }else {
            ACarACrime aCarACrime = (ACarACrime) getArguments().getSerializable("aCarACrime");
            initData(aCarACrime);
        }


        return view;
    }
    private void initView(){
        a_car_pepole_info_nodata=(LinearLayout)view.findViewById(R.id.a_car_pepole_crime_nodata);
        a_car_pepole_info_data=(LinearLayout)view.findViewById(R.id.a_car_pepole_crime_data);

        acapCrim_name    =(TextView)view.findViewById(R.id.acapCrim_name);
        acapCrim_tel     =(TextView)view.findViewById(R.id.acapCrim_tel);
        acapCrim_carNum  =(TextView)view.findViewById(R.id.acapCrim_carNum);
        acapCrim_num     =(TextView)view.findViewById(R.id.acapCrim_num);
        acapCrim_isztry  =(TextView)view.findViewById(R.id.acapCrim_isztry);
        acapCrim_isqkry  =(TextView)view.findViewById(R.id.acapCrim_isqkry);
        acapCrim_iszdry  =(TextView)view.findViewById(R.id.acapCrim_iszdry);
        acapCrim_issdry  =(TextView)view.findViewById(R.id.acapCrim_issdry);
        acapCrim_issary  =(TextView)view.findViewById(R.id.acapCrim_issary);
    }
    private void initData(ACarACrime aCarACrime){
        acapCrim_name.setText(aCarACrime.getName());
                acapCrim_tel.setText(aCarACrime.getTel());
        acapCrim_carNum.setText(aCarACrime.getType());
                acapCrim_num.setText(aCarACrime.getNumCard());
        acapCrim_isztry.setText(aCarACrime.getIsFugitive().equals("1")?"是":"否");
                acapCrim_isqkry.setText(aCarACrime.getIscrimeRecord().equals("1")?"是":"否");
        acapCrim_iszdry.setText(aCarACrime.getIsfocal().equals("1")?"是":"否");
                acapCrim_issdry.setText(aCarACrime.getIsDug().equals("1")?"是":"否");
        acapCrim_issary.setText(aCarACrime.getIsInvolved().equals("1")?"是":"否");
    }
}
