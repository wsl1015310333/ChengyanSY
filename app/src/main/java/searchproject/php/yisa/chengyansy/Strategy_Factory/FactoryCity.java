package searchproject.php.yisa.chengyansy.Strategy_Factory;

import android.content.Context;

/**
 * Created by Administrator on 2017/11/21 0021.
 */

public class FactoryCity {
    StrategyCity strategyCity;
    public static void setFactoryCity(StrategyCity strategyCity, int isNormal, Context context){
        strategyCity.operate(isNormal,context);
    }

}
