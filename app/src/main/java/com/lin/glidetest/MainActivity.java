package com.lin.glidetest;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class MainActivity extends AppCompatActivity {

    private ImageView mTargetView,mLocalView,mFlieView,mUriView;
    private ImageView mGifView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TODO: 2017/8/10 1.加载网络图片
        String url="http://www.qq745.com/uploads/allimg/141106/1-141106153Q5.png";
        mTargetView = (ImageView) findViewById(R.id.imageNet);
        Glide.with(this).load(url).asBitmap().into(mTargetView);
        // TODO: 2017/8/10 2.加载本地图片
        int resourceId=R.drawable.p1;
        mLocalView= (ImageView) findViewById(R.id.imageLocal);
        Glide.with(this).load(resourceId).asBitmap().into(mLocalView);
        // TODO: 2017/8/10 3.加载本地文件图片
        //模拟器没有SD卡  以后测试
//        mFlieView= (ImageView) findViewById(R.id.imageFlie);
//        File file=new File(Environment.getExternalStorageDirectory(),"p2.jpg");
//        Glide.with(this).load(file).asBitmap().into(mFlieView);
        // TODO: 2017/8/10 4.从Uri加载图片
        mUriView= (ImageView) findViewById(R.id.imageUri);
        Uri uri=Uri.parse("android.resource://"+this.getPackageName()+"/"+R.drawable.p1);
        Glide.with(this).load(uri).asBitmap().into(mUriView);
        // TODO: 2017/8/10 5.加载gif图片
        mGifView= (ImageView) findViewById(R.id.imageGif);
        Glide.with(this).load(R.drawable.p2).asGif().into(mGifView);
    }

}
