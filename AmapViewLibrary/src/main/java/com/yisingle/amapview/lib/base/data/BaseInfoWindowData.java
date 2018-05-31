package com.yisingle.amapview.lib.base.data;

/**
 * @author jikun
 * Created by jikun on 2018/4/19.
 */
public class BaseInfoWindowData<W> {
    private int type;


    private W data;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public W getData() {
        return data;
    }

    public void setData(W data) {
        this.data = data;
    }


    public BaseInfoWindowData(int type, W data) {
        this.type = type;

        this.data = data;
    }
}
