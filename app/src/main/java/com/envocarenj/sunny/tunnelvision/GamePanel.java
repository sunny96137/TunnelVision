package com.envocarenj.sunny.tunnelvision;

import android.content.Context;
import android.graphics.Canvas;

import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback{

    private MainThread thread;
    private RectPlayer player;
    private Point playerPoint;
    private int x;
    private int y;
    public GamePanel(Context context){
        super(context);
        Canvas canvas = new Canvas();
        x=canvas.getWidth()/2;
        y=getHeight()-50;
        getHolder().addCallback(this);

        thread = new MainThread(getHolder(),this);
        player = new RectPlayer(new Rect(100,100,200,200), Color.rgb(255,0,0));
        playerPoint = new Point(x,y);

        setFocusable(true);

    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height){


    }

    @Override
    public void surfaceCreated(SurfaceHolder holder){

        thread=new MainThread(getHolder(),this);

        thread.setRunning(true);
        thread.start();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

        boolean retry=true;
        while(true){

            try {
                thread.setRunning(false);
                thread.join();
            } catch (Exception e) {e.printStackTrace();}

            retry=false;
        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                playerPoint.set((int) event.getX(),(int)event.getY());

        }
        return true;

    }

    public void update(){
        player.update(playerPoint);
    }

    @Override
    public void draw(Canvas canvas){
        super.draw(canvas);
        canvas.drawColor(Color.WHITE);
        player.draw(canvas);

    }
}
