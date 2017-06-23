package com.example.addbook.tool;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

public class CompressJPEG {
	/**
     * 压缩图片
     *
     * @param image
     * @return
     */
    public static Bitmap compressImage(Bitmap image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 30, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 30;
        while (baos.toByteArray().length / 1024 > 30) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();//重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;//每次都减少10
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
        return bitmap;
    }
    
    
    
    @SuppressWarnings("deprecation")
	public static Drawable resizeImage(Bitmap bitmap, int w, int h)   
    {    
        Bitmap BitmapOrg = bitmap;    
        int width = BitmapOrg.getWidth();    
        int height = BitmapOrg.getHeight();    
        int newWidth = w;    
        int newHeight = h;    
  
        float scaleWidth = ((float) newWidth) / width;    
        float scaleHeight = ((float) newHeight) / height;    
  
        Matrix matrix = new Matrix();    
        matrix.postScale(scaleWidth, scaleHeight);    
        // if you want to rotate the Bitmap     
        // matrix.postRotate(45);     
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,    
                        height, matrix, true);    
        return new BitmapDrawable(resizedBitmap);    
    }  
}
