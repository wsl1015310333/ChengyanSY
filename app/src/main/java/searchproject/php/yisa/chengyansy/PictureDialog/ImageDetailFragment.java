package searchproject.php.yisa.chengyansy.PictureDialog;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import searchproject.php.yisa.chengyansy.R;

public class ImageDetailFragment extends Fragment {
    public static int mImageLoading;//占位符图片
    public static boolean mNeedDownload = false;//默认不支持下载
    private String mImageUrl;
    private ImageView mImageView;
    private PhotoViewAttacher mAttacher;
    private Bitmap mBitmap;
    public static Boolean isSuccessImage=false;
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    mBitmap= (Bitmap) msg.obj;
                    mImageView.setImageBitmap(mBitmap);
                    isSuccessImage=true;
                    break;
                case 201:
                    //  Toast.makeText(getActivity(),"图片加载失败！",Toast.LENGTH_SHORT).show();
                    mImageView.setImageResource(mImageLoading);
                    isSuccessImage=false;
                    break;
            }
        }
    };
    public static ImageDetailFragment newInstance(String imageUrl) {
        final ImageDetailFragment imageDetailFragment = new ImageDetailFragment();

        final Bundle args = new Bundle();
        args.putString("url", imageUrl);
        imageDetailFragment.setArguments(args);

        return imageDetailFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments() != null ? getArguments().getString("url") : null;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_image_detail, container, false);
        mImageView = (ImageView) v.findViewById(R.id.image);
        mAttacher = new PhotoViewAttacher(mImageView);
        mAttacher.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (isSuccessImage) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("保存图片");
                    builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ImageUtil.saveImage(getActivity(), mImageUrl, mBitmap);
                        }
                    });
                    builder.create().show();
                }
                return false;
            }
        });
        mAttacher.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

            @Override
            public void onPhotoTap(View arg0, float arg1, float arg2) {
                getActivity().finish();
            }
        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Picasso.with(getActivity()).load(mImageUrl)
                .into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        isSuccessImage=true;
                        mBitmap = bitmap;
                        mImageView.setImageBitmap(mBitmap);
                        mAttacher.update();

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        isSuccessImage=false;
                        mImageView.setImageResource(mImageLoading);
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Bitmap bitmap=      Picasso.with(getActivity()).load(mImageUrl).get();
                                    if(bitmap!=null){
                                        Message message=handler.obtainMessage();
                                        message.obj=bitmap;
                                        message.what=200;
                                        handler.sendMessage(message);
                                    }else {
                                        Log.e("11","11");
                                        Message message=handler.obtainMessage();
                                        message.what=201;
                                        handler.sendMessage(message);
                                    }
                                } catch (IOException e) {
                                    Log.e("22","22");
                                    Message message=handler.obtainMessage();
                                    message.what=201;
                                    handler.sendMessage(message);
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                });

    }
}
