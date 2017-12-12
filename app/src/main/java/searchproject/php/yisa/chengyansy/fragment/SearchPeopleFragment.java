package searchproject.php.yisa.chengyansy.fragment;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import searchproject.php.yisa.chengyansy.FilesUtils;
import searchproject.php.yisa.chengyansy.PersonOfSearch_ResultActivity;
import searchproject.php.yisa.chengyansy.R;
import searchproject.php.yisa.chengyansy.utils.HttpConnectionUtils;
import searchproject.php.yisa.chengyansy.utils.ResulutionJson;
import searchproject.php.yisa.chengyansy.view.ExpandLayout;


/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class SearchPeopleFragment extends Fragment {
    private TextInputEditText et_idCard;
    private LinearLayout sp_takePhoto;
    private View view;
    private Uri contentUri;
    private File newFile;
    private final int NEED_CAMERA =201;
    Button button_query;
    //对话框
    LinearLayout pop_camrea;
    LinearLayout pop_openImage;
    LinearLayout pop_cancel;
    PopupWindow mPopWindow;

    ImageView mIvArrow;
    ExpandLayout mContainerDes;
    private boolean isOpen=true;
LinearLayout sp_arrow_linearLayout;
    private Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 200:
                    List list= (List) msg.obj;
                    Intent intent=new Intent(getActivity(), PersonOfSearch_ResultActivity.class);
                    Bundle bundle=new Bundle();
                    bundle.putSerializable("list", (Serializable) list);
                    intent.putExtras(bundle);
                    startActivity(intent);
                    break;
                case 2000:
                    String listString = null;
                    String result=(String)msg.obj;
                    Intent intent1=new Intent(getActivity(), PersonOfSearch_ResultActivity.class);
                     List list1=  ResulutionJson.get_personJson(result);
                    Bundle bundle1=new Bundle();
                    bundle1.putSerializable("list", (Serializable) list1);
                    intent1.putExtras(bundle1);
                    startActivity(intent1);
                    break;

            }
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view= View.inflate(getActivity(), R.layout.fragment_searchpeople,null);
        initView();
        initData();
        return  view;
    }
    private void initView(){

        button_query=(Button)view.findViewById(R.id.button_query);
        et_idCard=(TextInputEditText) view.findViewById(R.id.et_idCard);
        sp_takePhoto=(LinearLayout)view.findViewById(R.id.sp_takePhoto);
        mIvArrow=(ImageView) view.findViewById(R.id.sp_arrow);
        mContainerDes=(ExpandLayout)view.findViewById(R.id.mContainerDes);
        mContainerDes.initExpand(false);
        sp_arrow_linearLayout=(LinearLayout)view.findViewById(R.id.sp_arrow_linearLayout);
    }
    private void initData(){
sp_takePhoto.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        showBottomPop(view);
    }
});
        sp_arrow_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContainerDes.toggleExpand();
            }
        });
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        HashMap hashMap=new HashMap();
                        hashMap.put("idCard ",et_idCard.getText().toString());
                        String token=   FilesUtils.sharedGetFile(getActivity(),"user","token");
                   //    String result=     HttpConnectionUtils.sendGETRequest("10.73.194.252:8080/api/get_person",hashMap,token);
                        String result=     HttpConnectionUtils.sendGETRequest("192.168.3.195:3000/api/get_person",hashMap,token);

                        if (result.equals(1)||result.equals("2")){
                            //空
                        }else{
                            List list=      ResulutionJson.get_personJson(result);
                            Message message=mHandler.obtainMessage();
                            message.what=200;
                            message.obj=list;
                            mHandler.sendMessage(message);
                        }


                    }
                }).start();
            }
        });
    }

    private void showBottomPop(View parent) {
        final View popView = View.inflate(getActivity(), R.layout.popupwindows_camrea, null);
        showAnimation(popView);//开启动画
        mPopWindow  = new PopupWindow(popView, LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        //  clickPopItem(popView, mPopWindow);//条目的点击
        pop_camrea=(LinearLayout)popView.findViewById(R.id.pop_camera);
        pop_openImage=(LinearLayout)popView.findViewById(R.id.pop_openImage);
        pop_cancel=(LinearLayout)popView.findViewById(R.id.pop_cancel);
        pop_camrea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED
                        || ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, NEED_CAMERA);
                } else {
                    startCamera();
                }
            }
        });
        pop_openImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");

                startActivityForResult(intent, 203);
            }
        });
        pop_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(null != mPopWindow && mPopWindow.isShowing()){
                    mPopWindow.dismiss();
                    if(null == mPopWindow){
                        Log.e("MainActivity","null == mPopupWindow");
                    }
                }
            }
        });
        mPopWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        mPopWindow.showAtLocation(parent,
                Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.update();
        // 设置背景颜色变暗
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
        lp.alpha = 0.7f;
        getActivity().getWindow().setAttributes(lp);

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {

            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }
    private void showAnimation(View popView) {
        AnimationSet animationSet = new AnimationSet(false);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1.0f);
        alphaAnimation.setDuration(300);
        TranslateAnimation translateAnimation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, 1f, Animation.RELATIVE_TO_SELF, 0f
        );
        translateAnimation.setDuration(300);
        animationSet.addAnimation(alphaAnimation);
        animationSet.addAnimation(translateAnimation);
        popView.startAnimation(animationSet);
    }

    /**
     * 打开相机获取图片
     */
    private void startCamera() {
        File imagePath = new File(Environment.getExternalStorageDirectory(), "images");
        if (!imagePath.exists()) imagePath.mkdirs();
        newFile = new File(imagePath, DateFormat.format("yyyyMMdd_hhmmss", Calendar.getInstance(Locale.CHINA))+ "default_image.jpg");

        //第二参数是在manifest.xml定义 provider的authorities属性
        contentUri = FileProvider.getUriForFile(getActivity(), "com.example.android.fileprovider", newFile);

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //兼容版本处理，因为 intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION) 只在5.0以上的版本有效
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            ClipData clip =
                    ClipData.newUri(getActivity().getContentResolver(), "A photo", contentUri);
            intent.setClipData(clip);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            List<ResolveInfo> resInfoList =
                    getActivity().getPackageManager()
                            .queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
            for (ResolveInfo resolveInfo : resInfoList) {
                String packageName = resolveInfo.activityInfo.packageName;
                getActivity().grantUriPermission(packageName, contentUri,
                        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        }
        intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        startActivityForResult(intent, NEED_CAMERA);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch(requestCode){
            case NEED_CAMERA:
                // 如果权限被拒绝，grantResults 为空
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startCamera();
                } else {
                    Toast.makeText(getActivity(), "功能需要相机和读写文件权限", Toast.LENGTH_SHORT).show();
                }
                break;
        }

    }
    public static String getRealFilePath(final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==NEED_CAMERA){
            ContentResolver contentProvider = getActivity().getContentResolver();
            ParcelFileDescriptor mInputPFD;
            try {
                //获取contentProvider图片
                mInputPFD = contentProvider.openFileDescriptor(contentUri, "r");
                FileDescriptor fileDescriptor = mInputPFD.getFileDescriptor();
                Log.e("contentUri",contentUri.toString());
                Log.e("newFile",newFile.toString());
                getActivity().sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));

                Intent intent=new Intent(getActivity(),PersonOfSearch_ResultActivity.class);
                Bundle bundle=new Bundle();

                bundle.putString("imgpath",newFile.toString());
                //请求网络
              intent.putExtras(bundle);
                startActivity(intent);
                //     mImageView.setImageBitmap(BitmapFactory.decodeFileDescriptor(fileDescriptor));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            Log.e("LocalUri","start");
            if(data!=null){
                Uri localUri = data.getData();
                Log.e("LocalUri",localUri.toString());
                final String imagepath=getRealFilePath(getActivity(),localUri);
                final String token = FilesUtils.sharedGetFile(getActivity(), "user", "token");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        //UploadUtil uploadUtil=UploadUtil.getInstance();
                      //  final String result=  uploadUtil.toUploadFile(imagepath,"file","http://192.168.3.195:3000/api/get_plate",null,token);///10.73.194.252:8088
                        final String result1=  HttpConnectionUtils.uploadFile(new File(imagepath),"http://"+HttpConnectionUtils.ipport+"/api/get_person",null,token);
                        mHandler.post(new Runnable() {
                            @Override
                            public void run() {
                              //  Toast.makeText(getActivity(),result,Toast.LENGTH_SHORT).show();
                                Toast.makeText(getActivity(),result1,Toast.LENGTH_SHORT).show();
                            }
                        });
                        if (result1!=null&&result1.length()>2){
                            Message message=mHandler.obtainMessage();
                            message.what=2000;
                            message.obj=result1;
                            mHandler.sendMessage(message);
                        }

                    }
                }).start();

//                Intent intent=new Intent(getActivity(),PersonOfSearch_ResultActivity.class);
//                Bundle bundle=new Bundle();
//            //    String imagepath=getRealFilePath(getActivity(),localUri);
//                bundle.putString("imgpath",imagepath);
//                intent.putExtras(bundle);
//                startActivity(intent);

            }
        }
    }

}
