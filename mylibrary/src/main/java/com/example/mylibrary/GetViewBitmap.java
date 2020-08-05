package com.example.mylibrary;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/*
* @作者:Hugh
* @时间:2020/1/4 17:13
* @作用:长截图+保存到相册+水印
*/

public class GetViewBitmap {
    private Context context;
    private View view;
    private String loge;
    private String path;

    public GetViewBitmap(Context context, String loge, String path, View view) {
        this.context = context;
        this.view = view;
        this.loge = loge;
        this.path = path;
        getViewBitmap(view);
    }

    /**
     * 截取View的屏幕
     **/
    public Bitmap getViewBitmap(View view) {
        Bitmap bitmap;
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_4444);
        final Canvas canvas = new Canvas(bitmap);
        //获取当前主题背景颜色，设置canvas背景
        canvas.drawColor(Color.parseColor("#ffffff"));
        //画文字水印，不需要的可删去下面这行
        view.draw(canvas);
        drawTextToBitmap(context, canvas, view.getMeasuredWidth(), view.getMeasuredHeight(),bitmap);
        return bitmap;
    }

    /**
     * 给图片添加水印
     *
     * @param context
     * @param canvas  画布
     * @param width   宽
     * @param height  高
     */
    private void drawTextToBitmap(Context context, Canvas canvas, int width, int height,Bitmap bitmap) {
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
        saveBmp2Gallery(bitmap);
    }

    private static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 保存图片到图库
     *
     * @param bmp
     */
    private void saveBmp2Gallery(Bitmap bmp) {
        //储存路径
        String galleryPath =  context.getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + "/dongdongjingji/camera/";
        // 声明文件对象
        File imagefile = new File(galleryPath);
        if (!imagefile.exists()){
            imagefile.mkdir();
        }
        File mShareImageFile = new File(imagefile,loge);
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
        MediaStore.Images.Media.insertImage(context.getContentResolver(),
                bmp, loge, null);
        Uri uri = Uri.fromFile(imagefile);
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(uri);
        context.sendBroadcast(intent);
        Toast.makeText(context, "图片保存成功", Toast.LENGTH_SHORT).show();
    }
}
