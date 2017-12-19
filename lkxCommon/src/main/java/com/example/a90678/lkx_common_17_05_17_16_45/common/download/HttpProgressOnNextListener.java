package com.example.a90678.lkx_common_17_05_17_16_45.common.download;

/**
 * 下载过程中的回调处理
 * Created by WZG on 2016/10/20.
 */
public abstract class HttpProgressOnNextListener<T> {
    /**
     * 成功后回调方法
     * @param t
     */
    public abstract void onNext(T t);

    /**
     * 开始下载
     */
    public abstract void onStart();

    /**
     * 完成下载
     */
    public abstract void onComplete();



    /**
     * 下载进度
     * @param readLength
     */
    public abstract void updateProgress(long readLength);

    /**
     * 失败或者错误方法
     * 主动调用，更加灵活
     * @param e
     */
     public  void onError(Throwable e){

     }

    /**
     * 暂停下载
     */
    public void onPuase(){

    }

    /**
     * 停止下载销毁
     */
    public void onStop(){

    }
}