package searchproject.php.yisa.chengyansy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import searchproject.php.yisa.chengyansy.Adapter_person.Person_resultAdapter;
import searchproject.php.yisa.chengyansy.Model_All.Person;
import searchproject.php.yisa.chengyansy.Model_All.Person_Detail;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.RefreshRecyclerView;

public class PersonOfSearch_ResultActivity extends AppCompatActivity {
    private List list=new ArrayList<>();
    private RefreshRecyclerView rv;
    private Person_resultAdapter adapter;
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch(msg.what) {
                case 200:
                    Intent intent =new Intent(PersonOfSearch_ResultActivity.this,PersonOfCar_DetailActivity.class);
                    Person_Detail person_detail = (Person_Detail) msg.obj;
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("person_detail", (Serializable) person_detail);
                 Log.e(person_detail.getAddress(),person_detail.getName());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 201:
                    break;
            }
        }
    };
    private LinearLayout return_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchpeople_result);
        return_=(LinearLayout)findViewById(R.id.img_return);
        return_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
       List list= (List) bundle.getSerializable("list");
        this.list=list;
        rv=(RefreshRecyclerView)findViewById(R.id.spr_recyclerview);
       // initView();
        initData();


    }
//    private void initView(){
//        for(int i=0;i<24;i++){
//            list.add(i);
//        }
//
//    }
    private void initData(){
        adapter=new Person_resultAdapter(list,PersonOfSearch_ResultActivity.this);
        rv.setLayoutManager(new GridLayoutManager(this,2));
        rv.setAdapter(adapter);
        adapter.setOnItemClickLitener(new Person_resultAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {
             final Person person= (Person) list.get(position);
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap hashMap=new HashMap();
                        hashMap.put("idCard",person.getIdCard());
                   String token=  FilesUtils.sharedGetFile(PersonOfSearch_ResultActivity.this,"user","token");
                      String result = HttpConnectionUtils.sendGETRequest(HttpConnectionUtils.ipport+"/api/person_detail",hashMap,token);
                        Log.e("person",result);
                        if(result.equals("1")||result.equals("2")){

                        }else {
                         Person_Detail person_detail= ResulutionJson.person_detailJson(result);
                            Log.e("","");
                            Message message=handler.obtainMessage();
                            message.what=200;
                            message.obj=person_detail;
                            handler.sendMessage(message);
                        }
                    }
                }).start();

            }
            @Override
            public void onItemLongClick(View view, int position) {
            }
        });
    }

}
