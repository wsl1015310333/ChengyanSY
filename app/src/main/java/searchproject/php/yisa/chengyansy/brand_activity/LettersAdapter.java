package searchproject.php.yisa.chengyansy.brand_activity;

/*
 *  项目名：  LettersNavigation 
 *  包名：    com.liuguilin.lettersnavigation.adapter
 *  文件名:   LettersAdapter
 *  创建者:   LGL
 *  创建时间:  2016/9/11 18:12
 *  描述：    联系人适配器
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.LettersModel;


public class LettersAdapter extends BaseAdapter {

    //上下文
    private Context mContext;
    //数据集
    private List<LettersModel> mList;
    //布局加载器
    private LayoutInflater mInflater;
    //实体类
    private LettersModel model;

    public LettersAdapter(Context mContext, List<LettersModel> mList) {
        this.mContext = mContext;
        this.mList = mList;

        //获取系统服务
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder vHolder = null;
        if (view == null) {
            vHolder = new ViewHolder();
            //加载布局
            view = mInflater.inflate(R.layout.list_item, null);

            vHolder.tvLetters = (TextView) view.findViewById(R.id.tvLetters);
            vHolder.tvName = (TextView) view.findViewById(R.id.tvName);
            view.setTag(vHolder);
        } else {
            vHolder = (ViewHolder) view.getTag();
        }
        //选中下标
        model = mList.get(i);
        //获取首字母显示人
        int firstPosition = getNmaeForPosition(i);
        //第一个
        int index = getPositionForNmae(firstPosition);
        //需要显示字母
        if (index == i) {

            vHolder.tvLetters.setVisibility(View.VISIBLE);
            vHolder.tvLetters.setText(model.getLetter());
        } else {

            vHolder.tvLetters.setVisibility(View.GONE);
        }
        vHolder.tvName.setText(model.getName());
        return view;
    }

    /**
     * 缓存优化
     */
    class ViewHolder {

        private TextView tvLetters;
        private TextView tvName;
    }

    /**
     * 通过首字母获取该首字母要显示的第一个人的下标
     *
     * @param position
     * @return
     */
    public int getPositionForNmae(int position) {
        for (int i = 0; i < getCount(); i++) {
            String letter = mList.get(i).getLetter();
            //首字母显示
            char firstChar = letter.toUpperCase().charAt(0);
            if (firstChar == position) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 根据名称拿到下标
     *
     * @param position
     * @return
     */
    private int getNmaeForPosition(int position) {
        return mList.get(position).getLetter().charAt(0);
    }

}
