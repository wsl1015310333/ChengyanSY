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


public class ModelActivity extends BaseLetterActivity {
    /**
     * 交换两个元素的位置的方法
     * @param strSort    需要交换元素的数组
     * @param i    索引i
     * @param j 索引j
     */
    private  void swap(String[] strSort, int i, int j) {
        String t = strSort[i];
        strSort[i] = strSort[j];
        strSort[j] = t;
    }
    /**
     * 对字符串进行由小到大排序
     * @param str    String[] 需要排序的字符串数组
     */
    public  String[] strSort(String[] str){
        for (int i = 0; i < str.length; i++) {
            for (int j = i+1; j < str.length; j++) {
//                if(str[i].compareTo(str[j])>0){    //对象排序用camparTo方法
//                    swap(str,i,j);
//                }
                if(compareTo(str[i],str[j])>0){    //对象排序用camparTo方法
                    swap(str,i,j);
                }
            }
        }
        return str;

    }
    @Override
    protected String[] exChangeChar(String[] args) {
        return strSort(args);
    }
    public  int compareTo(String s,String w)
    {
        int i = s.length();
        int j = w.length();
        int k = Math.min(i, j);
        char ac[] = s.toCharArray();
        char ac1[] = w.toCharArray();
        for(int l = 0; l < k; l++)
        {
            char c = ac[l];
            char c1 = ac1[l];
            if (Character.isDigit(c1)&&Character.isDigit(c)){
                String cc=s.substring(l,s.length());
                String c11=w.substring(l,w.length());
                if(canParseInt(cc)&&canParseInt(c11)) {
                    return (Integer.parseInt(cc) - (Integer.parseInt(c11)));
                }
                // return  cc.compareTo(c11);
            }
            if(c != c1) {


                return c - c1;
            }
        }

        return i - j;
    }

    public  boolean  canParseInt(String  str){
        if(str == null){
//验证是否为空
            return false;
        }
        return str.matches("\\d+");
//使用正则表达式判断该字符串是否为数字，第一个\是转义符，\d+表示匹配1个或  //多个连续数字，"+"和"*"类似，"*"表示0个或多个
    }
    @Override
    protected void confirm() {
        //Intent intent =new Intent() sendResult(200.intent)
//        Set set=map.entrySet();
//        Iterator iterator=set.iterator();
//        while (iterator.hasNext()){
//            String key=(String)iterator,next();
//        }
        if(BaseLetterActivity.mapModel!=null){
            BaseLetterActivity.mapModel.clear();
        }
        if(BaseLetterActivity.mapModel!=null){
            BaseLetterActivity.mapYear.clear();
        }
        Log.e("brand",mapBrand.get("brand"));
        Log.e("brandTitle",mapBrand.get("brandTitle"));
        Intent intent=new Intent(ModelActivity.this,MainActivity.class);

        SearchCarFragment.search_brandText.setText(mapBrand.get("brandTitle"));
        SearchCarFragment.fragment_carshowTitle.setVisibility(View.VISIBLE);
       startActivity(intent);

    }

    @Override
    protected List<Map<String, String>> getListData(String json) {
        Intent intent=getIntent();
        String resultKey=intent.getStringExtra("checkId");
        String title=intent.getStringExtra("edit");
        toolbar_title.setText(title);
        Model2 = AppJsonFileReader.setListData(json, resultKey);
        return Model2;
    }

    @Override
    void ClickAction(List<LettersModel> mList, int position) {
        String    s  =(String) mList.get(position).getName().toString();
      //  et_search.setText(s);
        Log.e("brand",mapBrand.get("brand"));
        Log.e("brandTitle",mapBrand.get("brandTitle"));
        for (Map<String,String>  m : Model1)
        {

            getText= m.get(s);
            Log.e(s,"        198   Brand      "+getText);

        }
        mapModel.put("model",getText);
        mapModel.put("modelTitle",s);
      //  String editText=(String)et_search.getText().toString().trim();
                Intent intent=new Intent(ModelActivity.this,NewYearActivity.class);
               intent.putExtra("edit",s);
               intent.putExtra("checkId",getText);
      //  Log.e("e",editText);
                startActivity( intent);

        // finish();
    }
}
