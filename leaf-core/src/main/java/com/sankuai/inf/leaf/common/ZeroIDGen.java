package com.sankuai.inf.leaf.common;

import com.sankuai.inf.leaf.IDGen;

public class ZeroIDGen implements IDGen {
    @Override
    public Result get(String key) {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public Result get() {
        return new Result(0, Status.SUCCESS);
    }

    @Override
    public ResultList list(String key, int length) {
        return new ResultList(null, Status.SUCCESS);
    }

    @Override
    public ResultList list(int length) {
        return new ResultList(null, Status.SUCCESS);
    }

    @Override
    public boolean init() {
        return true;
    }
}
