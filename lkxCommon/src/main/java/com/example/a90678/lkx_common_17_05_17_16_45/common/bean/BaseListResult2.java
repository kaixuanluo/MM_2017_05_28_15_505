package com.example.a90678.lkx_common_17_05_17_16_45.common.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 90678 on 2017/6/2.
 */

public abstract class BaseListResult2<ITEM> extends BaseListResult<ITEM> {

    private Result<ITEM> result;

    public Result<ITEM> getResult() {
        return result;
    }

    public static class Result<ITEM>  implements Serializable {

        List<ITEM> rows;

        public List<ITEM> getRows() {
            return rows;
        }
    }
}
