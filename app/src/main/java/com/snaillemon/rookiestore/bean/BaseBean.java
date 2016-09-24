package com.snaillemon.rookiestore.bean;

import java.io.Serializable;

/**
 * Created by frg on 9/23/2016.
 */

public class BaseBean implements Serializable {
    protected long id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
