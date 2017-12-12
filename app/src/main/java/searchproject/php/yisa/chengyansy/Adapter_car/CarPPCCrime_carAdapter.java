package searchproject.php.yisa.chengyansy.Adapter_car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.ACarAPPCrime;
import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/12/6 0006.
 */

public class CarPPCCrime_carAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULLDATA=2;
    private int load_more_status;
    Context context;
    private List<ACarAPPCrime> dataList;

  public  CarPPCCrime_carAdapter(List dataList,Context context){
        this.dataList = dataList;
        this.context=context;
    }
    public void changeMoreStatus(int status){
        load_more_status=status;
    }
    public interface OnItemClickLitener
    {
        void onItemACarAppClick(View view, int position);
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
    {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       if(viewType==TYPE_ITEM){
           MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.list_carppccrime, parent, false));
return myViewHolder;
       }
       else if(viewType==TYPE_NULLDATA){
           FootNullViewwHold FootNullViewwHold=new FootNullViewwHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_null,parent,false));
           return FootNullViewwHold;
       }else if (viewType == TYPE_FOOTER) {
           Log.e("datalist.size()",Integer.toString(dataList.size()%10));

           FootViewHold myViewHolder1 = new FootViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
           return myViewHolder1;

       }
        return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MyViewHolder){
            ((MyViewHolder)holder).numb.setText(dataList.get(position).getNumb()==null?"":dataList.get(position).getNumb().toString());
            ((MyViewHolder)holder).type.setText(dataList.get(position).getType()==null?"":dataList.get(position).getType().toString());
            ((MyViewHolder)holder).time.setText(dataList.get(position).getTime()==null?"":dataList.get(position).getTime().toString());
            ((MyViewHolder)holder).place.setText(dataList.get(position).getPlace()==null?"":dataList.get(position).getPlace().toString());
            ((MyViewHolder)holder).price.setText(dataList.get(position).getPrice()==null?"":dataList.get(position).getPrice().toString());
            ((MyViewHolder)holder).manager.setText(dataList.get(position).getManager()==null?"":dataList.get(position).getManager().toString());
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getImage_url();
//            String ip= LinkInfo.getIP(context);
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getPic();
//            Picasso.with(context)
////                    .load(dataList.get(position).getImage_url())
////                    .load("http://"+ip+":6253"+stringBuilder.substring(19))
//                    // .load("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24))
//                    //  .load(dataList.get(position).getPic())
//                    .load(HttpConnectionUtils.newString(dataList.get(position).getPic()))
//                    .resize(65, 50)
//                    .centerCrop()
//                    .into(((MyViewHolder)holder).imageView);
//            Mytask imageTask = new Mytask(((MyViewHolder)holder).imageView);
//            imageTask.execute("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24));

            if(mOnItemClickLitener!=null) {

//                ((MyViewHolder) holder).carinfo_acapp.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        int pos = holder.getLayoutPosition();
//                        //  Toast.makeText(context,"yicheyidang",Toast.LENGTH_SHORT).show();
//                        mOnItemClickLitener.onItemACarAppClick(holder.itemView, pos);
//                    }
//                });
                ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        //  Toast.makeText(context,"yicheyidang",Toast.LENGTH_SHORT).show();
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

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView time;
        private TextView place;
        private TextView price;
        private TextView manager;
        private TextView type;
        private TextView numb;
        public MyViewHolder(View itemView) {
            super(itemView);
            type=(TextView)itemView.findViewById(R.id.carpp_type);
            numb=(TextView)itemView.findViewById(R.id.carpp_numb);
            time=(TextView)itemView.findViewById(R.id.carpp_time);
             place=(TextView)itemView.findViewById(R.id.carpp_place);
            price=(TextView)itemView.findViewById(R.id.carpp_price);
            manager=(TextView)itemView.findViewById(R.id.carpp_manager);
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
            textView.setText("未找到符合条件的数据");
        }
    }
}
