package searchproject.php.yisa.chengyansy.Adapter_car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public abstract class BaseDefault_carAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public static final int TYPE_ITEM = 0;
    public static final int TYPE_FOOTER = 1;
    public static final int TYPE_NULLDATA=2;

    protected int load_more_status;
    Context context;
    public List<Car_resultJson> dataList;

//    public BaseDefault_carAdapter(List dataList , Context context) {
//        this.dataList = dataList;
//        this.context=context;
//    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }
    public BaseDefault_carAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(BaseDefault_carAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        return  AbstonCreateViewHolder (parent,  viewType);
    }
abstract RecyclerView.ViewHolder AbstonCreateViewHolder(ViewGroup parent, int viewType);
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        AbstonBindViewHolder(holder,position);
    }


abstract  void AbstonBindViewHolder(RecyclerView.ViewHolder holder, int position);


    public class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView plate;
        public ImageView imageView;
        public TextView model;
        public TextView locationName;
        public TextView captureTime;

        public MyViewHolder(View itemView) {
            super(itemView);
            captureTime= (TextView) itemView.findViewById(R.id.carinfo_captureTime);
            imageView= (ImageView) itemView.findViewById(R.id.car_image_url);
            plate= (TextView) itemView.findViewById(R.id.carinfo_plate);
            locationName= (TextView) itemView.findViewById(R.id.carinfo_locationName);
            model= (TextView) itemView.findViewById(R.id.carinfo_model);

        }
    }
    public class FootViewHold extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        TextView Loading;
        LinearLayout item_loadmore_container_retry;
        LinearLayout item_loadmore_container_loading;
        public FootViewHold(View itemView) {
            super(itemView);
            progressBar= (ProgressBar) itemView.findViewById(R.id.progressbar);
            Loading= (TextView) itemView.findViewById(R.id.loadIng);
            item_loadmore_container_retry=(LinearLayout)itemView.findViewById(R.id.item_loadmore_container_retry);
            item_loadmore_container_loading=(LinearLayout)itemView.findViewById(R.id.item_loadmore_container_loading);
        }
    }
    public class FootNullViewwHold extends RecyclerView.ViewHolder{
        TextView textView;
        public FootNullViewwHold(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.item_null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }

        else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0)?0:dataList.size();
    }

}

