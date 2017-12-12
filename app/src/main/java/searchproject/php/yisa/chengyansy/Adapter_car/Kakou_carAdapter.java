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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import searchproject.php.yisa.chengyansy.Model_All.Car_result_kk;
import searchproject.php.yisa.chengyansy.R;


/**
 * Author: zhuliyuan
 * Time: 下午 5:36
 */

public class Kakou_carAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private static final int TYPE_ITEM = 0;
    private static final int TYPE_FOOTER = 1;
    private static final int TYPE_NULLDATA = 2;

    private int load_more_status;
    Context context;
    private List<Car_result_kk> dataList;


    public Kakou_carAdapter(List dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }

    /**
     * //还有更多数据0;  *
     * //正在加载中
     * =1;
     * //没有更多数据
     * 加载失败2;
     *
     * @param status
     */
    public void changeMoreStatus(int status) {
        load_more_status = status;
    }


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public Kakou_carAdapter.OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(Kakou_carAdapter.OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            MyViewHolder myViewHolder = new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carmodel, parent, false));

            return myViewHolder;
        } else if (viewType == TYPE_NULLDATA) {
            FootNullViewwHold FootNullViewwHold = new FootNullViewwHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_null, parent, false));
            return FootNullViewwHold;
        }
        // type == TYPE_FOOTER 返回footerView
        else if (viewType == TYPE_FOOTER) {
            Log.e("datalist.size()", Integer.toString(dataList.size() % 10));

            FootViewHold myViewHolder1 = new FootViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
            return myViewHolder1;

        }

        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MyViewHolder) {
            ((Kakou_carAdapter.MyViewHolder) holder).text.setText(dataList.get(position).getText());
            ((Kakou_carAdapter.MyViewHolder) holder).count.setText(dataList.get(position).getNum());
//            ((MyViewHolder)holder).time_created.setText(dataList.get(position).getTime_created());
//            ((MyViewHolder)holder).car_Info.setText(dataList.get(position).getBrand_name()+"-"+dataList.get(position).getModel_name()+"-"+dataList.get(position).getYear_name());
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getImage_url();
//            String ip= LinkInfo.getIP(context);

//                  Picasso.with(context)
////                    .load(dataList.get(position).getImage_url())
////                    .load("http://"+ip+":6253"+stringBuilder.substring(19))
//                    .load("http://imgs.fjndwb.cn/2017/0731/20170731093700348.jpg")
//                    .resize(65, 50)
//                    .centerCrop()
//                    .into(((MyViewHolder)holder).imageView);
            if (mOnItemClickLitener != null) {
//                        ((MyViewHolder) holder).imageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        int pos = holder.getLayoutPosition();
//                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
//                    }
//                }
//                        );
                ((MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //    KakouFragment kakouFragment=new KakouFragment();
                        int pos = holder.getLayoutPosition();
                        //  kakouFragment.toResult(pos);
//
//                                Intent intent=new Intent(context, CarOfSearch_BayonetModel_ResultActivity.class);
//                                context.startActivity(intent);
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }

//                        holder.itemView.setOnClickListener(new View.OnClickListener() {
//                            @Override
//                            public void onClick(View v) {
//                                Toast.makeText(context,Integer.toString(position),Toast.LENGTH_SHORT).show();
//                                int pos = holder.getLayoutPosition();
//                                mOnItemClickLitener.onItemClick(holder.itemView,pos);
//                            }
//                        });

            //  }
        } else if (holder instanceof FootViewHold) {
            if (load_more_status == 1) {
                ((FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.GONE);
                ((FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.VISIBLE);
            } else if (load_more_status == 0) {
                ((FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.VISIBLE);
                ((FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.GONE);
            }
        }


    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout lineae;
        public TextView text;
        //    private ImageView imageView;
        public TextView count;
//        private TextView time_created;
//        private TextView car_Info;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.kakou_title);
            //    imageView= (ImageView) itemView.findViewById(R.id.car_image_url);
            count = (TextView) itemView.findViewById(R.id.kakou_count);
            lineae = (LinearLayout) itemView.findViewById(R.id.lineae);
        }
    }

    public class FootViewHold extends RecyclerView.ViewHolder {
        ProgressBar progressBar;
        TextView Loading;
        LinearLayout item_loadmore_container_retry;
        LinearLayout item_loadmore_container_loading;

        public FootViewHold(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressbar);
            Loading = (TextView) itemView.findViewById(R.id.loadIng);
            item_loadmore_container_retry = (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_retry);
            item_loadmore_container_loading = (LinearLayout) itemView.findViewById(R.id.item_loadmore_container_loading);
        }
    }

    public class FootNullViewwHold extends RecyclerView.ViewHolder {
        TextView textView;

        public FootNullViewwHold(View itemView) {
            super(itemView);
            textView=(TextView)itemView.findViewById(R.id.item_null);
        }
    }

    @Override
    public int getItemViewType(int position) {
        // 最后一个item设置为footerView
        // if (position + 1 == getItemCount()&&(dataList.size()%10)==0) {
        if (position + 1 == getItemCount()) {
            return TYPE_FOOTER;
        }
        // if (position+1==getItemCount()&&(dataList.size()%10)!=0){
       else {
            return TYPE_ITEM;
        }


    }

    @Override
    public int getItemCount() {
        return (dataList == null || dataList.size() == 0)?0:dataList.size()+1;
    }

}
