package searchproject.php.yisa.chengyansy.utils;

/*
 *  项目名：  LettersNavigation 
 *  包名：    com.liuguilin.lettersnavigation.utils
 *  文件名:   LettersSorting
 *  创建者:   LGL
 *  创建时间:  2016/9/11 18:04
 *  描述：    字母排序算法
 */


import java.util.Comparator;

public class LettersSorting implements Comparator<LettersModel> {

    @Override
    public int compare(LettersModel lettersModel, LettersModel t1) {
        //给我两个对象，我只比较他的字母
        return lettersModel.getLetter().compareTo(t1.getLetter());
    }
}
