package searchproject.php.yisa.chengyansy.fragement_carpp;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private TextView clyt;//c
    private TextView clsbdh;
    private TextView djzsbh;
    private TextView dabh;
    private TextView syxz;
    private TextView hdfs;
    private TextView syqxz;
    private TextView jdczt;
    private TextView dybj;
    private TextView zjdjrq;
    private TextView ycyxqz;
    private TextView qzbfqz;
    private TextView zsxxdz;
    private TextView zzxxdz;
    private TextView zzjzzm;
    private TextView ccdjrq;
    private TextView bxzzrq;
    private TextView gxsj;
    private TextView fprq;
    private TextView fhjzrq;
    private TextView fdjzsrq;
    private TextView bhlhszcs;
    private TextView bhlhpcs;
    private TextView bhlzscs;
    private TextView  glbm;
    private TextView acapInfo_color;
    private LinearLayout a_car_pepole_info_nodata;
    private LinearLayout a_car_pepole_info_data;

    ACarACrime aCarACrime;
    View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view= View.inflate(getActivity(), R.layout.fragment_carppinfo,null);
        initView();
        String isNO= (String) getArguments().getSerializable("isNull");
        if(isNO.equals("yes")){
            a_car_pepole_info_nodata.setVisibility(View.VISIBLE);
            a_car_pepole_info_data.setVisibility(View.GONE);
        }else {
            aCarACrime= (ACarACrime) getArguments().getSerializable("aCarACrime");
            String plate=(String)getArguments().getString("plate");
            Log.e("plate",plate);
            initData(aCarACrime,plate);
            a_car_pepole_info_nodata.setVisibility(View.GONE);
            a_car_pepole_info_data.setVisibility(View.VISIBLE);
        }




        return view;
    }
    private void initData(ACarACrime aCarACrime,String _plate){
        acapInfo_color.setText(car_color(aCarACrime.getColor()));
        name.setText(aCarACrime.getName());
        num.setText(aCarACrime.getNumCard());
        carType.setText(aCarACrime.getCllx());
        carNum.setText(aCarACrime.getClxh());
        plate.setText(_plate);
        numType.setText(aCarACrime.getType());
      //  acapInfo_plateType

        clyt   .setText(aCarACrime.getClyt());
        clsbdh.setText(aCarACrime.getClsbdh());
        djzsbh.setText(aCarACrime.getDjzsbh());
        dabh.setText(aCarACrime.getDabh());
        syxz.setText(using_nature(aCarACrime.getSyxz()));
        hdfs.setText(get_nature(aCarACrime.getHdfs()));
        syqxz.setText(all_nature(aCarACrime.getSyqxz()));
        jdczt.setText(car_nature(aCarACrime.getJdczt()));
        dybj.setText(aCarACrime.getDybj());
        zjdjrq.setText(aCarACrime.getZjdjrq());
        ycyxqz.setText(aCarACrime.getYcyxqz());
        qzbfqz.setText(aCarACrime.getQzbfqz());
        zsxxdz.setText(aCarACrime.getZsxxdz());
        zzxxdz.setText(aCarACrime.getZzxxdz());
        zzjzzm.setText(aCarACrime.getZzjzzm());
        ccdjrq.setText(aCarACrime.getCcdjrq());
        bxzzrq.setText(aCarACrime.getBxzzrq());
        gxsj.setText(aCarACrime.getGxsj());
        fprq.setText(aCarACrime.getFprq());
        fhjzrq.setText(aCarACrime.getBhlzscs());
        bhlhszcs.setText(aCarACrime.getBhlhszcs());
        bhlhpcs.setText(aCarACrime.getBhlhpcs());
        bhlzscs.setText(aCarACrime.getFhjzrq());
        glbm.setText(aCarACrime.getGlbm());
        acapInfo_color.setText(aCarACrime.getColor());

    }
    private void initView(){
        a_car_pepole_info_nodata=view.findViewById(R.id.a_car_pepole_info_nodata);
        a_car_pepole_info_data=view.findViewById(R.id.a_car_pepole_info_data);
        name=view.findViewById(R.id.acapInfo_name);
        num=view.findViewById(R.id.acapInfo_num);
        carType=view.findViewById(R.id.acapInfo_carType);
        carNum=view.findViewById(R.id.acapInfo_carNum);
        plate=view.findViewById(R.id.acapInfo_plate);
        numType=view.findViewById(R.id.acapInfo_numType);
        acapInfo_plateType=view.findViewById(R.id.acapInfo_plateType);

        clyt=view.findViewById(R.id.acapInfo_clyt);//c
        clsbdh=view.findViewById(R.id.acapInfo_clsbh);
        djzsbh=view.findViewById(R.id.acapInfo_djzsbh);
        dabh=view.findViewById(R.id.acapInfo_dabh);
        syxz=view.findViewById(R.id.acapInfo_syxz);
        hdfs=view.findViewById(R.id.acapInfo_hdfs);
        syqxz=view.findViewById(R.id.acapInfo_syqxz);
        jdczt=view.findViewById(R.id.acapInfo_jdczt);
        dybj=view.findViewById(R.id.acapInfo_dybz);
        zjdjrq=view.findViewById(R.id.acapInfo_zjdjrq);
        ycyxqz=view.findViewById(R.id.acapInfo_jcyxqz);
        qzbfqz=view.findViewById(R.id.acapInfo_qzbfqz);
        zsxxdz=view.findViewById(R.id.acapInfo_zsxxdz);
        zzxxdz=view.findViewById(R.id.acapInfo_zzxxdz);
        zzjzzm=view.findViewById(R.id.acapInfo_zzjzzm);
        ccdjrq=view.findViewById(R.id.acapInfo_ccdjrq);
        bxzzrq=view.findViewById(R.id.acapInfo_bxzzrq);
        gxsj=view.findViewById(R.id.acapInfo_gxsj);
        fprq=view.findViewById(R.id.acapInfo_fprq);
        fhjzrq=view.findViewById(R.id.acapInfo_fhjszrq);
        fdjzsrq=view.findViewById(R.id.acapInfo_fzdjzsrq);
        bhlhszcs=view.findViewById(R.id.acapInfo_bhlhcs);
        bhlhpcs=view.findViewById(R.id.acapInfo_bhlhpcs);
        bhlzscs=view.findViewById(R.id.acapInfo_bhlzscs);
        glbm=view.findViewById(R.id.acapInfo_glbm);
        acapInfo_color=view.findViewById(R.id.acapInfo_color);


    }
    //使用性质
    public String  using_nature (String use){
        HashMap hashMap=new HashMap();
        hashMap.put("A","非营运(A)");
        hashMap.put("B","公路客运(B)") ;
        hashMap.put("C","公交客运(C)");
        hashMap.put("D","出租车客运(D)");
        hashMap.put("E","旅游客运（E)");
        hashMap.put("F","货运(F)");
        hashMap.put("G","租赁(G)");
        hashMap.put("H","警用(H)");
        hashMap.put("I","消防(I)");
        hashMap.put("J","救护(J)");
        hashMap.put("K","工程抢险(K)");
        hashMap.put("L","营转非(L)");
        hashMap.put("M","出租转非(M)");
        hashMap.put("N","教练(N)");
        hashMap.put("R","化工(R)");
        hashMap.put("Z","其他(Z)");

        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if(key.equals(use)){
               return (String) entry.getValue();
            }

            }

                return "非营运 (A)";
    }
    //获得方式
    public String  get_nature (String use){
        HashMap hashMap=new HashMap();
        hashMap.put("A","购买(A)");
        hashMap.put("B","赠予(B)") ;
        hashMap.put("C","继承(C)");
        hashMap.put("D","调解(D)");
        hashMap.put("E","调拨（E)");
        hashMap.put("F","协议抵偿债务(F)");
        hashMap.put("G","军转(G)");
        hashMap.put("H","仲裁裁决(H)");
        hashMap.put("I","资产重组(I)");
        hashMap.put("J","资产整体买卖(J)");
        hashMap.put("K","裁定(K)");
        hashMap.put("L","判决(L)");
        hashMap.put("M","境外自带(M)");
        hashMap.put("N","中奖(N)");
        hashMap.put("Z","其他(Z)");

        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if(key.equals(use)){
                return (String) entry.getValue();
            }

        }

        return "其他 (A)";
    }
    //所有权性质
    public String all_nature(String use){
        HashMap hashMap=new HashMap();
        hashMap.put("1","单位(1)");
        hashMap.put("2","个人(2)");
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if(key.equals(use)){
                return (String) entry.getValue();
            }
        }
                return "其他(3)";
    }
    public String car_color(String use){
        HashMap hashMap =new HashMap();
        hashMap.put("A","白");
        hashMap.put("B","灰");
        hashMap.put("C","黄");
        hashMap.put("D","粉");
        hashMap.put("E","红");
        hashMap.put("F","紫");
        hashMap.put("G","绿");
        hashMap.put("H","蓝");
        hashMap.put("I","棕");
        hashMap.put("J","黑");
        hashMap.put("Z","其他");

        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if(key.equals(use)){
                return (String) entry.getValue();
            }
        }
        return "白(A)";
    }
    //机动车状态
    public String car_nature(String use){
        HashMap hashMap =new HashMap();
        hashMap.put("A","正常(A)");
        hashMap.put("B","转出(B)");
        hashMap.put("C","被盗抢(C)");
        hashMap.put("D","停驶(D)");
        hashMap.put("E","注销(E)");
        hashMap.put("G","违法未处理(G)");
        hashMap.put("H","海关监管(H)");
        hashMap.put("I","事故未处理(I)");
        hashMap.put("J","嫌疑车(J)");
        hashMap.put("K","查封(K)");
        hashMap.put("L","扣留(L)");
        hashMap.put("M","达到报废标准(M)");
        hashMap.put("N","事故逃逸(N)");
        hashMap.put("O","锁定(O)");
        hashMap.put("P","达到报废标准公告牌证作废(P)");
        hashMap.put("Q","逾期未检验(Q)");
        Iterator iter = hashMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            String key = (String) entry.getKey();
            if(key.equals(use)){
                return (String) entry.getValue();
            }
        }
        return "正常(A)";
    }
}
