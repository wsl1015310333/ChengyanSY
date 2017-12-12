package searchproject.php.yisa.chengyansy.Adapter_person;

/**
 * Created by Administrator on 2017/8/1 0001.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Person;
import searchproject.php.yisa.chengyansy.R;


/**
 * Author: zhuliyuan
 * Time: 下午 5:36
 */

public class Person_resultAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULLDATA=2;

    private int load_more_status;
    Context context;
    private List<Person> dataList;

    public Person_resultAdapter(List dataList , Context context) {
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
    }

    private Person_resultAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(Person_resultAdapter.OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newperson, parent, false));

            return myViewHolder;
//        }
//        else if(viewType==TYPE_NULLDATA){
//            FootNullViewwHold FootNullViewwHold=new FootNullViewwHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_null,parent,false));
//            return FootNullViewwHold;
//        }
//        // type == TYPE_FOOTER 返回footerView
//        else if (viewType == TYPE_FOOTER) {
//            Log.e("datalist.size()",Integer.toString(dataList.size()%10));
//
//            FootViewHold myViewHolder1 = new FootViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
//            return myViewHolder1;
//
        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){

            ((MyViewHolder)holder).person_idcard.setText(dataList.get(position).getIdCard());
            ((MyViewHolder)holder).person_name.setText(dataList.get(position).getName());
            ((MyViewHolder)holder).person_num.setText(dataList.get(position).getSimilar());
        //    ((MyViewHolder)holder).car_Info.setText(dataList.get(position).getBrand_name()+"-"+dataList.get(position).getModel_name()+"-"+dataList.get(position).getYear_name());
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getImage_url();
//            String ip= LinkInfo.getIP(context);

            Picasso.with(context)
//                    .load(dataList.get(position).getImage_url())
//                    .load("http://"+ip+":6253"+stringBuilder.substring(19))
                    .load("https://ss1.baidu.com/6ONXsjip0QIZ8tyhnq/it/u=622756147,107120481&fm=173&s=36AB6FA51E133BCC242E14230300E058&w=640&h=418&img.JPEG")
                    .resize(75, 50)
                    .centerCrop()
                    .into(((MyViewHolder)holder).imageView);
            if(mOnItemClickLitener!=null) {
                ((MyViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
        else if(holder instanceof FootViewHold){
            if(load_more_status==1){
                ((FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.GONE);
                ((FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.VISIBLE);
            }
            else if(load_more_status==0){
                ((FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.VISIBLE);
                ((FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.GONE);
            }
        }


    }



    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView person_name;
        private ImageView imageView;
        private TextView person_num;
        private TextView person_idcard;
     //   private TextView car_Info;

        public MyViewHolder(View itemView) {
            super(itemView);
            person_name= (TextView) itemView.findViewById(R.id.person_name);
            imageView= (ImageView) itemView.findViewById(R.id.search_Imgperson);
            person_num= (TextView) itemView.findViewById(R.id.person_num);
            person_idcard= (TextView) itemView.findViewById(R.id.person_idcard);
//            car_Info= (TextView) itemView.findViewById(R.id.car_Info);

        }
    }
    public class FootViewHold extends RecyclerView.ViewHolder{
        ProgressBar progressBar;
        TextView Loading;
        LinearLayout item_loadmore_container_retry;
        LinearLayout item_loadmore_container_loading;
        public FootViewHold(View itemView) {
            super(itemView);
//            progressBar= (ProgressBar) itemView.findViewById(R.id.progressbar);
//            Loading= (TextView) itemView.findViewById(R.id.loadIng);
//            item_loadmore_container_retry=(LinearLayout)itemView.findViewById(R.id.item_loadmore_container_retry);
//            item_loadmore_container_loading=(LinearLayout)itemView.findViewById(R.id.item_loadmore_container_loading);
        }
    }
    public class FootNullViewwHold extends RecyclerView.ViewHolder{
        TextView textView;
        public FootNullViewwHold(View itemView) {
            super(itemView);
            // textView=(TextView)itemView.findViewById(R.id.item_null);
        }
    }
    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        // if (position + 1 == getItemCount()&&(dataList.size()%10)==0) {
//        if (position + 1 == getItemCount()) {
//            return TYPE_FOOTER;
//        }
        // if (position+1==getItemCount()&&(dataList.size()%10)!=0){
//            return TYPE_NULLDATA;
//        }
//        else {
        return TYPE_ITEM;
        //      }
    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0)?0:dataList.size();
    }

}
