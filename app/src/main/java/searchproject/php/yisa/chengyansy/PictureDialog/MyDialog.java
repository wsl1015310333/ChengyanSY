package searchproject.php.yisa.chengyansy.PictureDialog;

/**
 * Created by Administrator on 2017/8/8 0008.
 */

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import searchproject.php.yisa.chengyansy.R;

/**
 * Created by jjy on 16-5-15.
 */
public class MyDialog extends Dialog {
    private ImageView image_loading;
    private TextView contenttv;

    public MyDialog(Context context, String name) {
        super(context,R.style.dialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.showsearchcardialog, null);  //通过LayoutInflater获取布局
      //  contenttv = (TextView) view.findViewById(R.id.title);
        image_loading = (ImageView) view.findViewById(R.id.image_loading);
        contenttv=(TextView)view.findViewById(R.id.showText);
        Glide.with(context).load("file:///android_asset/loadingGIF.gif").into(image_loading);
        contenttv.setText(name);
       // negativeButton = (Button) view.findViewById(R.id.refusebtn);
        setContentView(view);  //设置view
    }
//    //设置内容
//    public void setContent(String content) {
//        contenttv.setText(content);
//    }
//    //确定按钮监听
//    public void setOnPositiveListener(View.OnClickListener listener){
//        positiveButton.setOnClickListener(listener);
//    }
//
//    //否定按钮监听
//    public void setOnNegativeListener(View.OnClickListener listener){
//        negativeButton.setOnClickListener(listener);
//    }
}