package searchproject.php.yisa.chengyansy.PictureDialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import java.util.List;

import searchproject.php.yisa.chengyansy.R;


/**
 * 图片查看器
 * Created by zhuyong on 2017/5/12.
 */
public class ImagePagerActivity extends FragmentActivity {
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {

        }
    };

    private static final String TAG = "ImagePagerActivity";
    private static final String STATE_POSITION = "STATE_POSITION";
    public static final String EXTRA_IMAGE_INDEX = "image_index";
    public static final String EXTRA_IMAGE_URLS = "image_urls";

    public static boolean isFromPic=false;
    private HackyViewPager mPager;
    private int pagerPosition;
    private Button indicator;
    public static interface OnItemClickLitener
    {
       //  void  onItemClick(MyDialog myDialog);

        void onItemClick();

        void onItemLongClick(View view, int position);


    }

    public static ImagePagerActivity.OnItemClickLitener mOnItemClickLitener;

    public static void setOnItemClickLitener(ImagePagerActivity.OnItemClickLitener mOnItemClickLitener1)
    {
        mOnItemClickLitener = mOnItemClickLitener1;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        full(true);//设置沉浸
        setContentView(R.layout.activity_image_detail_pager);
        pagerPosition = getIntent().getIntExtra(EXTRA_IMAGE_INDEX, 0);
        List<String> urls = getIntent().getStringArrayListExtra(EXTRA_IMAGE_URLS);
        mPager = (HackyViewPager) findViewById(R.id.pager);
        ImagePagerAdapter mAdapter = new ImagePagerAdapter(
                getSupportFragmentManager(), urls);
        mPager.setAdapter(mAdapter);
        indicator = (Button) findViewById(R.id.indicator);

        CharSequence text = getString(R.string.viewpager_indicator, 1, mPager.getAdapter().getCount());
        if (isFromPic==false) {
            indicator.setVisibility(View.VISIBLE);
            indicator.setText("以图搜车");
            indicator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDiaglogCamr(true);

                    mOnItemClickLitener.onItemClick();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                           SystemClock.sleep(4000);
                            if (loadingDialogCamr!=null) {
                                handler.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadingDialogCamr.dismiss();
                                        finish();
                                    }
                                });

                            }

                        }
                    }).start();

                }
            });
        }else {
            indicator.setVisibility(View.GONE);
        }
        // 更新下标
        mPager.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageSelected(int arg0) {
//                CharSequence text = getString(R.string.viewpager_indicator,
//                        arg0 + 1, mPager.getAdapter().getCount());
//                indicator.setText(text);
            }

        });
        if (savedInstanceState != null) {
            pagerPosition = savedInstanceState.getInt(STATE_POSITION);
        }

        mPager.setCurrentItem(pagerPosition);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putInt(STATE_POSITION, mPager.getCurrentItem());
    }

    private class ImagePagerAdapter extends FragmentStatePagerAdapter {

        public List<String> fileList;

        public ImagePagerAdapter(FragmentManager fm, List<String> fileList) {
            super(fm);
            this.fileList = fileList;
        }

        @Override
        public int getCount() {
            return fileList == null ? 0 : fileList.size();
        }

        @Override
        public Fragment getItem(int position) {
            String url = fileList.get(position).toString();
            return ImageDetailFragment.newInstance(url);
        }

    }

    /**
     * 实现对状态栏的控制
     *
     * @param enable
     */
    private void full(boolean enable) {
        if (enable) {
            WindowManager.LayoutParams mLp = getWindow().getAttributes();
            mLp.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
            getWindow().setAttributes(mLp);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        } else {
            WindowManager.LayoutParams mAttr = getWindow().getAttributes();
            mAttr.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
            getWindow().setAttributes(mAttr);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    /**
     * 注意事项！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！！
     *
     * 本library中使用的图片加载框架是Picasso，如果你的主项目中也使用了Picasso请移除
     * ，使用本library中的即可，否则会报jar异常，或者可以下载本library替换加载框架也可以。
     * @param context
     * @param config
     */

    public static void startActivity(Context context, PictureConfig config) {
        Intent intent = new Intent(context, ImagePagerActivity.class);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_URLS, config.list);
        intent.putExtra(ImagePagerActivity.EXTRA_IMAGE_INDEX, config.position);
        ImageDetailFragment.mImageLoading = config.resId;
        ImageDetailFragment.mNeedDownload = config.needDownload;
        ImageUtil.path = config.path;
        context.startActivity(intent);
    }
    MyDialog   loadingDialogCamr;
    private void showDiaglogCamr(Boolean isShow){
        loadingDialogCamr=new MyDialog(ImagePagerActivity.this,"正在识别中···");
        loadingDialogCamr.getWindow().setLayout(DensityUtil.dip2px(ImagePagerActivity.this,278), DensityUtil.dip2px(ImagePagerActivity.this,122));
        loadingDialogCamr.setCanceledOnTouchOutside(false);
        loadingDialogCamr.show();

    }
}