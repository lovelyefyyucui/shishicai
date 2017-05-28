package com.shishicai.app.image;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;

public class ImageUtil {  
/** 
 * 获取网络address地址对应的图片 
 * @param address 
 * @return bitmap的类型  
 */  
    public static Bitmap getImage(String address) throws Exception{  
        //通过代码 模拟器浏览器访问图片的流程   
        URL url = new URL(address);  
        HttpURLConnection conn =  (HttpURLConnection) url.openConnection();  
        conn.setRequestMethod("GET");  
        conn.setConnectTimeout(5000);  
        //获取服务器返回回来的流   
        InputStream is = conn.getInputStream();  
        byte[] imagebytes = StreamTool.readInputStream(is);  
        Bitmap bitmap = BitmapFactory.decodeByteArray(imagebytes, 0, imagebytes.length);  
        return bitmap;  
    }  
    
    /** 
     * 把图片变成圆角 
     *  
     * @param bitmap 
     *            需要修改的图片 
     * @param pixels 
     *            圆角的弧度 
     * @return 圆角图片 
     */  
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {  
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap  
                .getHeight(), Config.ARGB_8888);  
        Canvas canvas = new Canvas(output);  
        final int color = 0xff424242;  
        final Paint paint = new Paint();  
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());  
        final RectF rectF = new RectF(rect);  
        final float roundPx = pixels;  
        paint.setAntiAlias(true);  
        canvas.drawARGB(0, 0, 0, 0);  
        paint.setColor(color);  
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);  
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));  
        canvas.drawBitmap(bitmap, rect, rect, paint);  
        return output;  
    }

    public static Bitmap getBitmap(String path){
        BitmapFactory.Options bfOptions=new BitmapFactory.Options();
        bfOptions.inDither=false;
        bfOptions.inPurgeable=true;
        bfOptions.inTempStorage= new byte[12 * 1024];
        bfOptions.inJustDecodeBounds = true;
        File file = new File(path);
        FileInputStream fs=null;
        Bitmap bmp = null;
        try {
            fs = new FileInputStream(file);
            if(fs != null)
                bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null, bfOptions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }
}  
