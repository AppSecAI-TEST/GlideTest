package com.lin.glidetest;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.Resource;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapResource;

public class MainActivity extends AppCompatActivity {

    private ImageView mTargetView,mLocalView,mFlieView,mUriView;
    private ImageView mGifView;
    private ImageView mView6,mView7,mView8,mView9,mView10,mView11;
    private ImageView mView12,mView13,mView14,mView15,mView16,mView17;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TODO: 2017/8/10 1.加载网络图片
        String url="http://www.qq745.com/uploads/allimg/141106/1-141106153Q5.png";
        mTargetView = (ImageView) findViewById(R.id.imageNet);
        Glide.with(this)
                .load(url)
                .asBitmap()
                .transform(new CropCircleTransformation(this))
                .into(mTargetView);
        // TODO: 2017/8/10 2.加载本地图片
        int resourceId=R.drawable.p1;
        mLocalView= (ImageView) findViewById(R.id.imageLocal);
        Glide.with(this)
                .load(resourceId)
                .asBitmap()
                .transform(new CropCircleTransformation(this))
                .into(mLocalView);
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

        // TODO: 2017/8/11 6. 设置默认图片和加载失败时显示的图片
        mView6= (ImageView) findViewById(R.id.image6);
        Glide.with(this).load(R.drawable.p1).asBitmap()
                .placeholder(R.drawable.p3) //加载中显示图片
                .error(R.drawable.p4)//加载失败时显示的图片
                .into(mView6);
        // TODO: 2017/8/11 7. 淡入效果显示
        //另外,crossFade还可以接收一个参数来设置淡入显示效果的持续时间,crossFade(int duration);
        //如果你想直接显示图片,而不是淡入显示图片,则可以通过dontAnimate()方法设置.

        mView7= (ImageView) findViewById(R.id.image7);
        Glide.with(this)
                .load(R.drawable.p3)
                .placeholder(R.drawable.p1)
                .error(R.drawable.p2)
                .crossFade(10000)  // 淡入显示，注：如果设置了这个，则必须去掉asBitmap;
                .into(mView7);

        // TODO: 2017/8/11 8.调整图片像素大小
        mView8= (ImageView) findViewById(R.id.image8);
        Glide.with(this)
                .load(R.drawable.p4)
                .placeholder(R.drawable.p1)
                .error(R.drawable.p3)
                .crossFade(10000)
                .override(150,150)//社会最终显示图片像素为100*80 注意：这个是像素，而不是控件的宽高
                .into(mView8);
        // TODO: 2017/8/11 9. 设置CenterCrop,FitCenter
        /*
        CenterCrop,FitCenter都是对目标图片进行裁剪,
        了解过ImageView的ScaleType属性就知道,
        这2种裁剪方式在ImageView上也是有的,
        分别对应ImageView的ImageView.ScaleType.CENTER_CROP
        和mageView.ScaleType.FIT_CENTER的.
         */
        mView9= (ImageView) findViewById(R.id.image9);
        Glide.with(this).
                load(R.drawable.p3).
                placeholder(R.drawable.p1).//加载中显示的图片
                error(R.drawable.p4).//加载失败时显示的图片
                crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                override(100,100).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                centerCrop().//中心裁剪,缩放填充至整个ImageView
                into(mView9);
        // TODO: 2017/8/11 10. 缓存策略设置
        //内存缓存设置,通过skipMemoryCache(boolean)来设置是否需要缓存到内存,默认是会缓存到内存的.
        mView10= (ImageView) findViewById(R.id.image10);
        Glide.with(this).
                load(R.drawable.p1).
                placeholder(R.drawable.p3).//加载中显示的图片
                error(R.drawable.p4).//加载失败时显示的图片
                crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                override(100,100).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                centerCrop().//中心裁剪,缩放填充至整个ImageView
                skipMemoryCache(true).//跳过内存缓存
                into(mView10);
        /*
        磁盘缓存,磁盘缓存通过diskCacheStrategy(DiskCacheStrategy)来设置,DiskCacheStrategy一共有4种模式:
        DiskCacheStrategy.NONE:什么都不缓存
        DiskCacheStrategy.SOURCE:仅缓存原图(全分辨率的图片)
        DiskCacheStrategy.RESULT:仅缓存最终的图片,即修改了尺寸或者转换后的图片
        DiskCacheStrategy.ALL:缓存所有版本的图片,默认模式
         */

        mView11= (ImageView) findViewById(R.id.image11);
        Glide.with(this).
                load(R.drawable.p1).
                placeholder(R.drawable.p3).//加载中显示的图片
                error(R.drawable.p4).//加载失败时显示的图片
                crossFade(1000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                override(100,100).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                centerCrop().//中心裁剪,缩放填充至整个ImageView
                skipMemoryCache(true).//跳过内存缓存
                diskCacheStrategy(DiskCacheStrategy.RESULT).//保存最终图片
                into(mView11);
        // TODO: 2017/8/11 12.缓存设置
        //在GlideModule 中,我们可以设置磁盘缓存的位置,磁盘缓存的大小和内存缓存的大小,同时还可以设置图片的显示质量.
        //要是用GlideModule ,需要创建它的实现类,然后在manifests中申明实现类的全类路径
        // TODO: 2017/8/11  14.设置加载缩略图
        mView14= (ImageView) findViewById(R.id.image14);
        Glide.with(this).
                load(R.drawable.p4).
                placeholder(R.drawable.p3).//加载中显示的图片
                error(R.drawable.p2).//加载失败时显示的图片
                crossFade(10000).//淡入淡出,注意:如果设置了这个,则必须要去掉asBitmap
                override(80, 80).//设置最终显示的图片像素为80*80,注意:这个是像素,而不是控件的宽高
                centerCrop().//中心裁剪,缩放填充至整个ImageView
                skipMemoryCache(true).//跳过内存缓存
                diskCacheStrategy(DiskCacheStrategy.RESULT).//保存最终图片
                thumbnail(0.1f).//10%的原图大小
                into(mView14);
    }
    //圆型处理
    public class CropCircleTransformation implements Transformation<Bitmap> {

        private BitmapPool mBitmapPool;

        public CropCircleTransformation(Context context) {
            this(Glide.get(context).getBitmapPool());
        }

        public CropCircleTransformation(BitmapPool pool) {
            this.mBitmapPool = pool;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();
            int size = Math.min(source.getWidth(), source.getHeight());

            int width = (source.getWidth() - size) / 2;
            int height = (source.getHeight() - size) / 2;

            Bitmap bitmap = mBitmapPool.get(size, size, Bitmap.Config.ARGB_8888);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            BitmapShader shader =
                    new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
            if (width != 0 || height != 0) {
                // source isn't square, move viewport to center
                Matrix matrix = new Matrix();
                matrix.setTranslate(-width, -height);
                shader.setLocalMatrix(matrix);
            }
            paint.setShader(shader);
            paint.setAntiAlias(true);

            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override public String getId() {
            return "CropCircleTransformation()";
        }
    }
    //圆角处理
    public class RoundedCornersTransformation implements Transformation<Bitmap> {

        private BitmapPool mBitmapPool;

        private int radius;
        private int margin;

        public RoundedCornersTransformation(Context context, int radius, int margin) {
            this(Glide.get(context).getBitmapPool(), radius, margin);
        }

        public RoundedCornersTransformation(BitmapPool pool, int radius, int margin) {
            mBitmapPool = pool;
            this.radius = radius;
            this.margin = margin;
        }

        @Override
        public Resource<Bitmap> transform(Resource<Bitmap> resource, int outWidth, int outHeight) {
            Bitmap source = resource.get();

            int width = source.getWidth();
            int height = source.getHeight();

            Bitmap bitmap = mBitmapPool.get(width, height, Bitmap.Config.ARGB_8888);
            if (bitmap == null) {
                bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
            }

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setShader(new BitmapShader(source, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP));
            canvas.drawRoundRect(new RectF(margin, margin, width - margin, height - margin), radius, radius,
                    paint);

            return BitmapResource.obtain(bitmap, mBitmapPool);
        }

        @Override public String getId() {
            return "RoundedTransformation(radius=" + radius + ", margin=" + margin + ")";
        }
    }
}
