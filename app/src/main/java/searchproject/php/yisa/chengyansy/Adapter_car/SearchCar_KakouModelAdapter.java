package searchproject.php.yisa.chengyansy.Adapter_car;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Car_resultJson;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;


/**
 * Author: zhuliyuan
 * Time: 下午 5:36
 */

public class SearchCar_KakouModelAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULLDATA=2;

    private int load_more_status;
    Context context;
    private List<Car_resultJson> dataList;

    public SearchCar_KakouModelAdapter(List dataList , Context context) {
        this.dataList = dataList;
        this.context=context;
    }

    /**
     * //还有更多数据0;  *
     * //正在加载中
     * =1;
     * //没有更多数据
     * 加载失败2;
     * @param status
     */
    public void changeMoreStatus(int status){
        load_more_status=status;
    }
    public interface OnItemClickLitener
    {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);

        void onItemACarAppClick(View itemView, int pos);
    }

    private SearchCar_KakouModelAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(SearchCar_KakouModelAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            SearchCar_KakouModelAdapter.MyViewHolder myViewHolder = new SearchCar_KakouModelAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carinfo, parent, false));

            return myViewHolder;
        }
        else if(viewType==TYPE_NULLDATA){
            SearchCar_KakouModelAdapter.FootNullViewwHold FootNullViewwHold=new SearchCar_KakouModelAdapter.FootNullViewwHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_null,parent,false));
            return FootNullViewwHold;
        }
        // type == TYPE_FOOTER 返回footerView
        else if (viewType == TYPE_FOOTER) {
            Log.e("datalist.size()",Integer.toString(dataList.size()%10));

            SearchCar_KakouModelAdapter.FootViewHold myViewHolder1 = new SearchCar_KakouModelAdapter.FootViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
            return myViewHolder1;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof SearchCar_KakouModelAdapter.MyViewHolder){

            ((SearchCar_KakouModelAdapter.MyViewHolder)holder).plate.setText(dataList.get(position).getPlate()==null?"":dataList.get(position).getPlate().toString());
            ((SearchCar_KakouModelAdapter.MyViewHolder)holder).model.setText(dataList.get(position).getModel()==null?"":dataList.get(position).getModel().toString());
            ((SearchCar_KakouModelAdapter.MyViewHolder)holder).locationName.setText(dataList.get(position).getLocationName()==null?"":dataList.get(position).getLocationName().toString());
            ((SearchCar_KakouModelAdapter.MyViewHolder)holder).captureTime.setText(dataList.get(position).getCaptureTime()==null?"":dataList.get(position).getCaptureTime().toString());
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getImage_url();
//            String ip= LinkInfo.getIP(context);
            String stringBuilder=new String();
            stringBuilder= dataList.get(position).getPic();
            Picasso.with(context)
//                    .load(dataList.get(position).getImage_url())
//                    .load("http://"+ip+":6253"+stringBuilder.substring(19))
                    //.load("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24))
                    //  .load(dataList.get(position).getPic())
                    .load(HttpConnectionUtils.newString(dataList.get(position).getPic()))
                    .resize(65, 50)
                    .centerCrop()
                    .into(((SearchCar_KakouModelAdapter.MyViewHolder)holder).imageView);
            if(mOnItemClickLitener!=null) {
                ((MyViewHolder) holder).carinfo_acap.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int pos = holder.getLayoutPosition();
                        //  Toast.makeText(context,"yicheyidang",Toast.LENGTH_SHORT).show();
                        mOnItemClickLitener.onItemACarAppClick(holder.itemView, pos);
                    }
                });
                
                ((SearchCar_KakouModelAdapter.MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
        else if(holder instanceof SearchCar_KakouModelAdapter.FootViewHold){
            if(load_more_status==1){
                ((SearchCar_KakouModelAdapter.FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.GONE);
                ((SearchCar_KakouModelAdapter.FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.VISIBLE);
            }
            else if(load_more_status==0){
                ((SearchCar_KakouModelAdapter.FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.VISIBLE);
                ((SearchCar_KakouModelAdapter.FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.GONE);
            }
        }




    }



public class MyViewHolder extends RecyclerView.ViewHolder{
    private TextView plate;
    private ImageView imageView;
    private TextView model;
    private TextView locationName;
    private TextView captureTime;
    private ImageButton carinfo_acap;


    public MyViewHolder(View itemView) {
        super(itemView);
        captureTime= (TextView) itemView.findViewById(R.id.carinfo_captureTime);
        imageView= (ImageView) itemView.findViewById(R.id.car_image_url);
        plate= (TextView) itemView.findViewById(R.id.carinfo_plate);
        locationName= (TextView) itemView.findViewById(R.id.carinfo_locationName);
        model= (TextView) itemView.findViewById(R.id.carinfo_model);
        carinfo_acap=(ImageButton)itemView.findViewById(R.id.carinfo_acap);
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
        return (dataList == null || dataList.size() == 0)?0:dataList.size()+1;
    }

}
