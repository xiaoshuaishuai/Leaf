package com.sankuai.inf.leaf;

import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ResultList;

public interface IDGen {
    Result get(String key);
    Result get();
    ResultList list(String key, int length);
    ResultList list(int length);
    boolean init();
}
