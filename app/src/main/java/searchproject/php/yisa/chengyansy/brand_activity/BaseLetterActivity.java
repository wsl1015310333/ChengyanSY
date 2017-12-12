package searchproject.php.yisa.chengyansy.brand_activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.AppJsonFileReader;
import searchproject.php.yisa.chengyansy.utils.CharSetUtil;
import searchproject.php.yisa.chengyansy.utils.LettersModel;
import searchproject.php.yisa.chengyansy.utils.LettersSorting;
import searchproject.php.yisa.chengyansy.utils.Trans2PinYin;
import searchproject.php.yisa.chengyansy.view.LettersView;


public abstract class BaseLetterActivity extends AppCompatActivity implements LettersView.OnLettersListViewListener, View.OnClickListener {
    private final static String fileName = "branddata.json";
    private static final String TAG = "Letters";

    //联系人列表
    private ListView mListView;
    //字母提示
    private TextView tvToast;
    //字母列表
    private LettersView mLettersView;
    //清除按钮
    private ImageView tv_cancel;
    //搜索框
    private EditText et_search;
    //列表数据
    private List<LettersModel> mList = new ArrayList<LettersModel>();
    //数据源
    private LettersAdapter adapter;
    //联系人数据模拟
//    private String[] strName = {"张三", "李四", "李七", "刘某人", "王五", "Android", "IOS", "王寡妇",
//            "阿三", "爸爸", "妈妈", "CoCo", "弟弟", "尔康", "紫薇", "小燕子", "皇阿玛", "福尔康", "哥哥", "Hi", "I", "杰克", "克星", "乐乐", "你好", "Oppo", "皮特", "曲奇饼",
//            "日啊", "思思", "缇娜", "U", "V", "王大叔", "嘻嘻", "一小伙子", "撒贝宁", "吱吱", "舅舅", "老总", "隔壁老王", "许仙"};
    private String [] strr;
    private Intent intent;
    private   Bundle bundle;
    private Button iv_search;



    LinearLayout img_return;
    LinearLayout toolbar_comfirm;
    TextView toolbar_title;

   public  static Map <String,String>mapBrand=new HashMap<>();
 public  static   Map <String,String>mapModel=new HashMap<>();
 public  static   Map <String,String>mapYear=new HashMap<>();

    List<Map<String, String>> Model1;
    List<Map<String, String>>  Model2;
    public static String getText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_letter);
        img_return=(LinearLayout)findViewById(R.id.img_return);
        toolbar_comfirm=(LinearLayout)findViewById(R.id.toolbar_comfirm);
        toolbar_title=(TextView)findViewById(R.id.toolbar_title);

initJson();
    }
    protected  void initJson(){
        String jsonStr = AppJsonFileReader.getJson(getBaseContext(),

                fileName);
        String jsonStrJ= CharSetUtil.decodeUnicode(jsonStr);
        strr=null;
        List<String> listStirng=new ArrayList<String>();
        Model1=getListData(jsonStr);


        for(Map<String,String> hash : Model1){
            for (Map.Entry<String, String> s : hash.entrySet()) {
                System.out.println("键值对:" + s);
            }
        }
        for (Map<String,String>  m : Model1)//为了转化为数组
        {
            for (String k : m.keySet())
            {
                listStirng.add(k);
                System.out.println(k + " Brand: " + m.get(k));
            }
        }
        int size = listStirng.size();


        String[] arr = (String[])listStirng.toArray(new String[size]);//使用了第二种接口，返回值和参数均为结果
        strr=exChangeChar(arr);
        strr=arr;
        initView();
        img_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        toolbar_comfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirm();
            }
        });
    }
    protected abstract String[] exChangeChar(String []args);
    protected  abstract  void confirm();
    protected  abstract   List<Map<String, String>> getListData(String json);
    private void initView() {
        // iv_search=(Button)findViewById(R.id.iv_search);
        tv_cancel = (ImageView) findViewById(R.id.tv_cancel);
        tv_cancel.setOnClickListener(this);
        mListView = (ListView) findViewById(R.id.mListView);
        tvToast = (TextView) findViewById(R.id.tvToast);
        et_search = (EditText) findViewById(R.id.et_search);
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    tv_cancel.setVisibility(View.VISIBLE);
                } else {
                    tv_cancel.setVisibility(View.GONE);
                }
            }
        });
        mLettersView = (LettersView) findViewById(R.id.mLettersView);
        mLettersView.setmTextView(tvToast);
        //绑定
        mLettersView.setOnLettersListViewListener(this);

        //加载联系人的模拟数据
        mList = parsingData();

//        System.out.println(mList.get(2).toString());
        //对字母进行排序A-Z #
        Collections.sort(mList, new LettersSorting());
        for(LettersModel attribute : mList) {
            System.out.println("11"+attribute.toString());
            System.out.println("11"+attribute.getName());
        }
        //加载适配器
        adapter = new LettersAdapter(this,mList);
        //设置数据
        mListView.setAdapter(adapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ClickAction(mList,position);
//                String    s  =(String) mList.get(position).getName().toString();
//                et_search.setText(s);
//
//                for (Map<String,String>  m : Model1)
//                {
//
//                    getText= m.get(s);
//                    Log.e(s,"        198   Brand      "+getText);
//
//                }
//                String editText=(String)et_search.getText().toString().trim();
//                Intent intent=new Intent(ListBrandActivity.this,ListModel.class);
//                //   intent.putExtra("edit",s);
//                intent.putExtra("checkId",getText);
//                Log.e("e",editText);
//                startActivity( intent);

                // finish();
            }



        });
    }
    abstract  void ClickAction(List<LettersModel> mList,int position);
    /**
     * 联系人数组转换实体数据
     *
     * @return
     */
    private List<LettersModel> parsingData() {
        List<LettersModel> listModel = new ArrayList<>();

        Log.i(TAG, " strName.length:" + strr.length);
        for (int i = 0; i < strr.length; i++) {
            LettersModel model = new LettersModel();
            model.setName(strr[i]);
            Log.i(TAG, strr[i]);
            //转换拼音截取首字母并且大写
            String pinyin = Trans2PinYin.trans2PinYin(strr[i].trim());
            Log.i(TAG, "pinyin:" + pinyin);
            String letter = pinyin.substring(0, 1).toUpperCase();
            //     Log.i(TAG, "letter:" + letter);
            model.setLetter(letter);
            listModel.add(model);
        }
        return listModel;
    }

    /**
     * ListView与字母导航联动
     *
     * @param s
     */
    @Override
    public void onLettersListener(String s) {
        //对应的位置
        int position = adapter.getPositionForNmae(s.charAt(0));
        //移动
        mListView.setSelection(position);
    }

    /**
     * 点击事件
     *
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:
                et_search.setText("");
                tv_cancel.setVisibility(View.GONE);
                break;
        }

    }
    @Override
    public void onBackPressed() {

        finish();
    }

}
