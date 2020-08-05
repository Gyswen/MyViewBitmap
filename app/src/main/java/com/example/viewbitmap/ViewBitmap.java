package com.example.viewbitmap;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/*
* @作者:Hugh
* @时间:2020/1/4 17:13
* @作用:截图+保存到相册+水印
*/

public class ViewBitmap {
    private static ViewBitmap viewBitmap = null;
    private Context context;
    private String loge;
    private String path;
    private Paint paint;
    private Canvas canvas;
    private Bitmap bitmap;

    public static ViewBitmap getInstance(Context context, String loge, String path,Paint paint) {
        if (viewBitmap == null) {
            synchronized (ViewBitmap.class) {
                if (viewBitmap == null) {
                    viewBitmap = new ViewBitmap(context,loge,path,paint);
                }
            }
        }
        return viewBitmap;

    }

    public ViewBitmap(Context context, String loge, String path,Paint paint) {
        this.context = context;
        this.loge = loge;
        this.path = path;
        this.paint = paint;
        if (paint == null){

        }
    }

    /**
     * 截取View的屏幕
     **/
    public boolean getViewBitmap(View view,int style) {
        boolean result;
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        //获取当前主题背景颜色，设置canvas背景
        canvas.drawColor(Color.parseColor("#ffffff"));
        view.draw(canvas);
        result = setStyle(view,style);
        return result;
    }

    private boolean setStyle(View view,int style){
        boolean result = false;
        switch (style){
            case 0:
                result = drawTextToBitmap(context, canvas, view.getMeasuredWidth(), view.getMeasuredHeight(),bitmap);
                break;
            case 1:
                result = drawTextToBitmap(context, canvas, view.getMeasuredWidth(), view.getMeasuredHeight(),bitmap,paint);
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;
        }
        return result;
    }

    /**
     * 给图片添加水印 样式1
     *
     * @param context
     * @param canvas  画布
     * @param width   宽
     * @param height  高
     */
    private boolean drawTextToBitmap(Context context, Canvas canvas, int width, int height,Bitmap bitmap) {
        //新建画笔，默认style为实心
        Paint paint = new Paint();
        //设置颜色，颜色可用Color.parseColor("#6b99b9")代替
        paint.setColor(Color.parseColor("#006b99b9"));
        //设置透明度
        paint.setAlpha(80);
        //抗锯齿
        paint.setAntiAlias(true);
        //画笔粗细大小
        paint.setTextSize((float) sp2px(context, 30));
        //保存当前画布状态
        canvas.save();
        //画布旋转-30度
        canvas.rotate(-30);
        //获取要添加文字的宽度
        float textWidth = paint.measureText(loge);
        int index = 0;
        //行循环，从高度为0开始，向下每隔80dp开始绘制文字
        for (int positionY = -sp2px(context, 30); positionY <= height; positionY += sp2px(context, 80)) {
            //设置每行文字开始绘制的位置,0.58是根据角度算出tan30°,后面的(index++ % 2) * textWidth是为了展示效果交错绘制
            float fromX = -0.58f * height + (index++ % 2) * textWidth;
            //列循环，从每行的开始位置开始，向右每隔2倍宽度的距离开始绘制（文字间距1倍宽度）
            for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                //绘制文字
                canvas.drawText(loge, positionX, positionY, paint);
            }
        }
        //恢复画布状态
        canvas.restore();
        //Android 10 保存方法与Andriud 10以下版本不一样,所以需要判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return saveBmp2GalleryQ(bitmap);
        }else {
            return saveBmp2Gallery(bitmap);
        }
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 给图片添加水印 样式2
     *
     * @param context
     * @param canvas  画布
     * @param width   宽
     * @param height  高
     */
    private boolean drawTextToBitmap(Context context, Canvas canvas, int width, int height,Bitmap bitmap,Paint paint) {
        //保存当前画布状态
        canvas.save();
        //画布旋转-30度
        canvas.rotate(-30);
        //获取要添加文字的宽度
        float textWidth = paint.measureText(loge);
        int index = 0;
        //行循环，从高度为0开始，向下每隔80dp开始绘制文字
        for (int positionY = -sp2px(context, 30); positionY <= height; positionY += sp2px(context, 80)) {
            //设置每行文字开始绘制的位置,0.58是根据角度算出tan30°,后面的(index++ % 2) * textWidth是为了展示效果交错绘制
            float fromX = -0.58f * height + (index++ % 2) * textWidth;
            //列循环，从每行的开始位置开始，向右每隔2倍宽度的距离开始绘制（文字间距1倍宽度）
            for (float positionX = fromX; positionX < width; positionX += textWidth * 2) {
                //绘制文字
                canvas.drawText(loge, positionX, positionY, paint);
            }
        }
        //恢复画布状态
        canvas.restore();
        //Android 10 保存方法与Andriud 10以下版本不一样,所以需要判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            return saveBmp2GalleryQ(bitmap);
        }else {
            return saveBmp2Gallery(bitmap);
        }
    }

    /**
     * 把视频保存到ContentProvider,在选择上传的时候能找到
     */
    private void saveImageInfo(File file) {
        try {
            String fileName = file.getName();
            long currentTimeMillis = System.currentTimeMillis();
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.TITLE, fileName);
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            values.put(MediaStore.MediaColumns.DATE_MODIFIED, currentTimeMillis);
            values.put(MediaStore.MediaColumns.DATE_ADDED, currentTimeMillis);
            values.put(MediaStore.MediaColumns.SIZE, file.length());
            values.put(MediaStore.MediaColumns.DATA, file.getAbsolutePath());
            values.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取时间戳
     * @return
     */
    private String getTime(){
        Long tsLong = System.currentTimeMillis()/1000;
        String ts = tsLong.toString();
        return ts;
    }

    /**
     * 保存图片到图库
     * Android Q适配
     * @param bmp
     */
    private boolean saveBmp2GalleryQ(Bitmap bmp) {
        boolean result = false;
        //储存路径
        String galleryPath =  context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/" + path;
        // 声明文件对象
        File imagefile = new File(galleryPath);
        if (!imagefile.exists()){
            imagefile.mkdir();
        }
        File mShareImageFile = new File(imagefile,getTime());
        if (mShareImageFile.exists()){
            mShareImageFile.delete();
        }
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, mShareImageFile.getName());
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, mShareImageFile.getName());
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        // 声明输出流
        OutputStream foc = null;
        try {
            // 获得输出流，如果文件中有内容，追加内容
            foc = context.getContentResolver().openOutputStream(uri);
            if (null != foc) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, foc);
            }
            foc.flush();
            result = true;
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (null != foc) {
                try {
                    foc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return result;
    }

    /**
     * 保存图片到图库
     * @param bmp
     */
    private boolean saveBmp2Gallery(Bitmap bmp) {
        boolean result = false;
        //储存路径
        String galleryPath =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath() + "/" + path;
        // 声明文件对象
        File imagefile = new File(galleryPath);
        if (!imagefile.exists()){
            imagefile.mkdir();
        }
        File mShareImageFile = new File(imagefile,getTime());
        if (mShareImageFile.exists()){
            mShareImageFile.delete();
        }
        // 声明输出流
        FileOutputStream foc = null;
        try {
            // 获得输出流，如果文件中有内容，追加内容
            foc = new FileOutputStream(mShareImageFile);
            if (null != foc) {
                bmp.compress(Bitmap.CompressFormat.JPEG, 100, foc);
            }
            foc.flush();
            result = true;
        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (null != foc) {
                try {
                    foc.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        saveImageInfo(imagefile);
        Uri uri = Uri.fromFile(imagefile);
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));
        return result;
    }
}
