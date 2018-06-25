package com.yisingle.amapview.lib.utils;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

/**
 * 手机角度帮助类
 *
 * @author jikun
 */
@SuppressWarnings("unused")
public class SensorEventHelper implements SensorEventListener {

    private final String TAG = SensorEventHelper.class.getSimpleName();
    private SensorManager mSensorManager;
    private Sensor magneticSensor, accelerometerSensor;
    private long lastTime = 0;
    private final int TIME_SENSOR = 100;
    private float mAngle;
    private Context mContext;

    private OnRotationListener onRotationListener;

    private float[] values, r, gravity, geomagnetic;


    public SensorEventHelper(Context context) {
        mContext = context;
        mSensorManager = (SensorManager) context
                .getSystemService(Context.SENSOR_SERVICE);
        if (null != mSensorManager) {

            //磁力传感器
            magneticSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
            //加速度传感器
            accelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

            //初始化数组
            //用来保存最终的结果
            values = new float[3];
            //用来保存加速度传感器的值
            gravity = new float[3];
            r = new float[9];
            //用来保存地磁传感器的值
            geomagnetic = new float[3];
            if (null != magneticSensor && null != accelerometerSensor) {
                init();
            } else {
                Log.e(TAG, TAG + ":" + getErrorInfo());
            }
        }

    }

    private void init() {
        registerSensorListener();
    }

    public void destroySensorEventHelper() {
        unRegisterSensorListener();
        mSensorManager = null;
        magneticSensor = null;
        accelerometerSensor = null;
        mContext = null;
    }

    private void registerSensorListener() {
        mSensorManager.registerListener(this, magneticSensor,
                SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this, accelerometerSensor,
                SensorManager.SENSOR_DELAY_NORMAL);

    }

    private void unRegisterSensorListener() {
        if (null != mSensorManager) {
            mSensorManager.unregisterListener(this, magneticSensor);
            mSensorManager.unregisterListener(this, accelerometerSensor);
        }

    }


    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {


    }


    public OnRotationListener getOnRotationListener() {
        return onRotationListener;
    }

    public void setOnRotationListener(OnRotationListener onRotationListener) {
        this.onRotationListener = onRotationListener;
        if (null != onRotationListener) {
            if (null == magneticSensor || null == accelerometerSensor) {

                onRotationListener.onRotationFailed(getErrorInfo());
            }
        }
    }

    private String getErrorInfo() {
        return (magneticSensor == null ? "设备没有磁力传感器" : "") +
                (accelerometerSensor == null ? "设备没有加速度传感器为" : "");
    }


    @Override
    public void onSensorChanged(SensorEvent event) {


        if (event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD) {
            geomagnetic = event.values;
        }
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            gravity = event.values;
            getValue();

        }


    }

    private void getValue() {
        // r从这里返回
        SensorManager.getRotationMatrix(r, null, gravity, geomagnetic);
        //values从这里返回
        SensorManager.getOrientation(r, values);
        //提取数据
        double azimuth = Math.toDegrees(values[0]);
        if (azimuth < 0) {
            azimuth = azimuth + 360;
        }
        double pitch = Math.toDegrees(values[1]);
        double roll = Math.toDegrees(values[2]);

    }


    public interface OnRotationListener {

        /**
         * 角度回调
         *
         * @param angle 角度
         */
        void onRotationChange(float angle);

        /**
         * 角度回调
         *
         * @param erroInfo 错误信息
         */
        void onRotationFailed(String erroInfo);


    }
}