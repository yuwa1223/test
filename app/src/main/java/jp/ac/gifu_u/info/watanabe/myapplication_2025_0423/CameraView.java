package jp.ac.gifu_u.info.watanabe.myapplication_2025_0423;

import android.content.Context;
import android.hardware.Camera;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraView extends SurfaceView implements SurfaceHolder.Callback{
    private Camera cam;

    private SurfaceHolder holder;

    public CameraView(Context context){
        super(context);

        holder=getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        cam =Camera.open(0);
        try{cam.setPreviewDisplay(holder);}
        catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder,int format,int width,int height){
        cam.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder){
        cam.setPreviewCallback(null);
        cam.stopPreview();
        cam.release();
        cam =null;
    }

}