package searchproject.php.yisa.chengyansy.brand_activity;

import android.content.Intent;
import android.util.Log;

import java.util.List;
import java.util.Map;

import searchproject.php.yisa.chengyansy.utils.AppJsonFileReader;
import searchproject.php.yisa.chengyansy.utils.LettersModel;


public class BrandActivity extends BaseLetterActivity {


    @Override
    protected String[] exChangeChar(String[] args) {
        return args;
    }

    @Override
    protected void confirm() {
        finish();
    }

    @Override
    protected List<Map<String, String>> getListData(String jsonStrJ ) {
       return AppJsonFileReader.setListDataBrand(jsonStrJ);
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
        /// String editText=(String)et_search.getText().toString().trim();
           Intent intent=new Intent(BrandActivity.this,ModelActivity.class);
           intent.putExtra("edit",s);
           intent.putExtra("checkId",getText);
           mapBrand.put("brand",getText);
           mapBrand.put("brandTitle",s);

        Log.e("brand  ----",mapBrand.get("brand"));
        Log.e("brandTitle   -------",mapBrand.get("brandTitle"));
       // map.put(s,getText);
        //  Log.e("e",editText);
        startActivityForResult(intent,300);
      //  startActivity( intent);
    }


}
