package searchproject.php.yisa.chengyansy.Adapter_car;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.util.List;

import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;

/**
 * Created by Administrator on 2017/8/14 0014.
 */

public class Default_searchCarAdapter extends BaseDefault_carAdapter {
    public Default_searchCarAdapter(List dataList1, Context context1) {
        dataList = dataList1;
        context=context1;
    }

    @Override
    RecyclerView.ViewHolder AbstonCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_ITEM) {
            Default_searchCarAdapter.MyViewHolder myViewHolder = new Default_searchCarAdapter.MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carinfo, parent, false));

            return myViewHolder;
        }
        else if(viewType==TYPE_NULLDATA){
            Default_searchCarAdapter.FootNullViewwHold FootNullViewwHold=new Default_searchCarAdapter.FootNullViewwHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_null,parent,false));
            return FootNullViewwHold;
        }
        // type == TYPE_FOOTER 返回footerView
        else if (viewType == TYPE_FOOTER) {
            Log.e("datalist.size()",Integer.toString(dataList.size()%10));

            Default_searchCarAdapter.FootViewHold myViewHolder1 = new Default_searchCarAdapter.FootViewHold(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_footer, parent, false));
            return myViewHolder1;

        }

        return null;
    }

    @Override
    void AbstonBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof Default_carAdapter.MyViewHolder){

            ((BaseDefault_carAdapter.MyViewHolder)holder).plate.setText(dataList.get(position).getPlate()==null?"":dataList.get(position).getPlate().toString());
            ((BaseDefault_carAdapter.MyViewHolder)holder).model.setText(dataList.get(position).getModel()==null?"":dataList.get(position).getModel().toString());
            ((BaseDefault_carAdapter.MyViewHolder)holder).locationName.setText(dataList.get(position).getLocationName()==null?"":dataList.get(position).getLocationName().toString());
            ((BaseDefault_carAdapter.MyViewHolder)holder).captureTime.setText(dataList.get(position).getCaptureTime()==null?"":dataList.get(position).getCaptureTime().toString());
//            String stringBuilder=new String();
//            stringBuilder= dataList.get(position).getImage_url();
//            String ip= LinkInfo.getIP(context);
            String stringBuilder=new String();
            stringBuilder= dataList.get(position).getPic();
            Picasso.with(context)
//                    .load(dataList.get(position).getImage_url())
//                    .load("http://"+ip+":6253"+stringBuilder.substring(19))
                    .load("http://"+ HttpConnectionUtils.ipport+stringBuilder.substring(24))
                    //  .load(dataList.get(position).getPic())
                    .resize(65, 50)
                    .centerCrop()
                    .into(((BaseDefault_carAdapter.MyViewHolder)holder).imageView);
            if(mOnItemClickLitener!=null) {
                ((BaseDefault_carAdapter.MyViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int pos = holder.getLayoutPosition();
                        mOnItemClickLitener.onItemClick(holder.itemView, pos);
                    }
                });
            }
        }
        else if(holder instanceof Default_carAdapter.FootViewHold){
            if(load_more_status==1){
                ((Default_carAdapter.FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.GONE);
                ((Default_carAdapter.FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.VISIBLE);
            }
            else if(load_more_status==0){
                ((Default_carAdapter.FootViewHold) holder).item_loadmore_container_loading.setVisibility(View.VISIBLE);
                ((Default_carAdapter.FootViewHold) holder).item_loadmore_container_retry.setVisibility(View.GONE);
            }
        }
    }
}
