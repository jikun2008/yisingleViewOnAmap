package com.yisingle.amapview.lib.utils;


import java.util.ArrayList;
import java.util.List;

/**
 * @author jikun
 * Created by jikun on 2018/6/21.
 * 这个是工具类是用来进行数组
 */
public class TwoArrayUtils {


    public interface Listener<S, E> {


        void onOneMore(S more, S remain);


        void onTwoMore(E more, E remain);


        void onSizeEqual();


    }

    /**
     * 用来取出两个数组中 取出多的数组 并通过listener返回
     *
     * @param oneList  第一个数组
     * @param twoList  第二个数组
     * @param listener 监听器
     * @param <O>      泛型
     * @param <T>      泛型
     */
    public static <O, T> void looperCompare(List<O> oneList, List<T> twoList, Listener<List<O>, List<T>> listener) {
        int maxSize = 0;
        int littleSize = 0;
        boolean isOneThanTwo = false;
        if (null != oneList && null != twoList) {
            isOneThanTwo = oneList.size() > twoList.size();
            maxSize = isOneThanTwo ? oneList.size() : twoList.size();
            littleSize = !isOneThanTwo ? oneList.size() : twoList.size();
        } else {
            if (oneList == null) {
                if (twoList != null) {
                    listener.onTwoMore(twoList, twoList);
                } else {
                    listener.onSizeEqual();
                }
            } else {
                listener.onOneMore(oneList, oneList);
            }
            return;
        }

        List<O> newO = new ArrayList<>();
        List<O> remainO = new ArrayList<>();
        List<T> newT = new ArrayList<>();
        List<T> remainT = new ArrayList<>();


        if (maxSize == littleSize) {
            listener.onSizeEqual();
            return;
        }

        for (int i = 0; i < maxSize; i++) {
            if (isOneThanTwo) {
                if (i >= littleSize) {
                    newO.add(oneList.get(i));
                } else {
                    remainO.add(oneList.get(i));
                }


            } else if (!isOneThanTwo) {
                if (i >= littleSize) {
                    newT.add(twoList.get(i));
                } else {
                    remainT.add(twoList.get(i));
                }

            }
        }
        if (isOneThanTwo) {
            listener.onOneMore(newO, remainO);
        } else {
            listener.onTwoMore(newT, remainT);
        }

    }


}
