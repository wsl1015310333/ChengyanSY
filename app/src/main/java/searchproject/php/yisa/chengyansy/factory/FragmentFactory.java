package searchproject.php.yisa.chengyansy.factory;

import android.support.v4.app.Fragment;
import android.support.v4.util.SparseArrayCompat;

import searchproject.php.yisa.chengyansy.fragment.BukongyujingFragment;
import searchproject.php.yisa.chengyansy.fragment.SearchCarFragment;
import searchproject.php.yisa.chengyansy.fragment.ZhoubianyujingFragment;


/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class FragmentFactory {
    public static final int FRAGMENT_ZHOUBIANYUJING=1;
    public static final int FRAGMENT_BUKONGGAOJING=2;
    public static final int FRAGMENT_SEARCHPEOPLE=3;
    public static final int FRAGMENT_SEARCHCAR=0;
    static SparseArrayCompat<Fragment> sparseArrayCompa=new SparseArrayCompat<Fragment>();
    public static Fragment getRragment(int position){
        Fragment fragment=null;
        Fragment tmpFragment=sparseArrayCompa.get(position);
        if(tmpFragment!=null){
            fragment =tmpFragment;
            return  fragment;
        }
        switch (position){
            case FRAGMENT_ZHOUBIANYUJING:
              fragment=new ZhoubianyujingFragment();
break;
            case FRAGMENT_BUKONGGAOJING:
                fragment=new BukongyujingFragment();
                break;
//            case FRAGMENT_SEARCHPEOPLE:
//                fragment= new SearchPeopleFragment();
//                break;
            case FRAGMENT_SEARCHCAR:
                fragment= new SearchCarFragment();
                break;
        }
        sparseArrayCompa.put(position,fragment);
        return  fragment;
    }
}
