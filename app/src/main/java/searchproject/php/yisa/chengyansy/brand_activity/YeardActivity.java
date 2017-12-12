package searchproject.php.yisa.chengyansy.brand_activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.MainActivity;
import searchproject.php.yisa.chengyansy.fragment.SearchCarFragment;
import searchproject.php.yisa.chengyansy.utils.AppJsonFileReader;
import searchproject.php.yisa.chengyansy.utils.LettersModel;


public class YeardActivity extends BaseLetterActivity {


    @Override
    protected String[] exChangeChar(String[] args) {
        return args;
    }

    @Override
    protected void confirm() {

        Intent intent=new Intent(YeardActivity.this,MainActivity.class);

        SearchCarFragment.search_brandText.setText(mapBrand.get("brandTitle")+"-"+mapModel.get("modelTitle"));
        SearchCarFragment.fragment_carshowTitle.setVisibility(View.VISIBLE);
        startActivity(intent);
    }

    @Override
    protected List<Map<String, String>> getListData(String json) {
        Intent intent=getIntent();
        String resultKey=intent.getStringExtra("checkId");
        toolbar_title.setText(mapBrand.get("brandTitle")+"-"+mapModel.get("modelTitle"));

        return AppJsonFileReader.setListDataYear(json,resultKey);
    }

    @Override
    void ClickAction(List<LettersModel> mList, int position) {
        String    s  =(String) mList.get(position).getName().toString();
        //  et_search.setText(s);

        for (Map<String,String>  m : Model1)
        {

            getText= m.get(s);
            Log.e(s,"        198   Brand      "+getText);

        }
        mapYear.put("year",getText);
        mapYear.put("yearTitle",s);
        SearchCarFragment.search_brandText.setText(mapBrand.get("brandTitle")+"-"+mapModel.get("modelTitle")+"-"+mapYear.get("yearTitle"));
        //  String editText=(String)et_search.getText().toString().trim();
        SearchCarFragment.fragment_carshowTitle.setVisibility(View.VISIBLE);
        Intent intent=new Intent(YeardActivity.this,MainActivity.class);
        //   intent.putExtra("edit",s);
        intent.putExtra("checkId",getText);
        //  Log.e("e",editText);
        startActivity( intent);
    }
}
