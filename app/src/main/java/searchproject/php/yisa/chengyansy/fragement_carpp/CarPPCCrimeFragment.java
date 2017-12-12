package searchproject.php.yisa.chengyansy.fragement_carpp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import searchproject.php.yisa.chengyansy.Adapter_car.CarPPCCrime_carAdapter;
import searchproject.php.yisa.chengyansy.Model_All.ACarAPPCrime;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

/**
 * Created by Administrator on 2017/12/3 0003.
 */

public class CarPPCCrimeFragment extends Fragment {
    private static List<ACarAPPCrime> dataList;
    private RefreshRecyclerView rv;
    //a_car_pepole_carppccrime_recyclerview
    View view;
    CarPPCCrime_carAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (isAdded()) {//判断Fragment已经依附Activity
//            productId = getArguments().getString(Constant.INTENT_ID);
        }
        view= View.inflate(getActivity(), R.layout.fragment_carppccrime,null);
        dataList =new ArrayList<>();
        //获取网络数据
        ACarAPPCrime aCarAPPCrime=new ACarAPPCrime();
        aCarAPPCrime.setManager("孙波");
        aCarAPPCrime.setPlace("京东大厦前进一路");
        aCarAPPCrime.setPrice("200");
        aCarAPPCrime.setTime("2017/11/22 12:22:11");
        aCarAPPCrime.setType("机动车违反禁止停车标志指示");
        aCarAPPCrime.setNumb("0");
        dataList.add(aCarAPPCrime);
        dataList.add(aCarAPPCrime);
        dataList.add(aCarAPPCrime);
        initData();
        return view;

    }
    private void initData(){
        rv=view.findViewById(R.id.a_car_pepole_carppccrime_recyclerview);
        rv.setFooterResource(R.layout.item_footer);
        adapter=new CarPPCCrime_carAdapter(dataList,getActivity());
        rv.setLayoutManager(new LinearLayoutManager((getActivity())));
        rv.setAdapter(adapter);

    }

}
