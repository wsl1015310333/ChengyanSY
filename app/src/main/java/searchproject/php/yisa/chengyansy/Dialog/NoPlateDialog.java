package searchproject.php.yisa.chengyansy.Dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import searchproject.php.yisa.chengyansy.R;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class NoPlateDialog extends Dialog implements View.OnClickListener {
    private OnDialogButtonClickListener buttonClickListner;
    private ImageView image_loading;
    private TextView contenttv;
    View view;
    LinearLayout  id_first;
    Button button;
    public NoPlateDialog(Context context) {
        super(context, R.style.dialog);
        view = LayoutInflater.from(getContext()).inflate(R.layout.shownoplatedialog, null);  //通过LayoutInflater获取布局
        //  contenttv = (TextView) view.findViewById(R.id.title);
        //  image_loading = (ImageView) view.findViewById(R.id.image_loading);
        //  Glide.with(context).load("file:///android_asset/loadingGIF.gif").into(image_loading);
        // negativeButton = (Button) view.findViewById(R.id.refusebtn);
        setContentView(view);  //设置view

    }
    public void setOnButtonClickListener(OnDialogButtonClickListener listener) {
        this.buttonClickListner = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id_first=(LinearLayout)view.findViewById(R.id.id_first);
        button=(Button) view.findViewById(R.id.button);
        id_first.setOnClickListener(this);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClickListner.okButtonClick();
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.id_first:
                Log.e("click","click");
                break;
        }
    }

    //实现回调功能
    public interface OnDialogButtonClickListener {
        public void okButtonClick();
        public void cancelButtonClick();

    }
}
