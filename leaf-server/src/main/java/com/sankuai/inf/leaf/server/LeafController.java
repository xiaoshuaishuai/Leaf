package com.sankuai.inf.leaf.server;

import com.google.common.collect.Lists;
import com.sankuai.inf.leaf.common.Result;
import com.sankuai.inf.leaf.common.ResultList;
import com.sankuai.inf.leaf.common.Status;
import com.sankuai.inf.leaf.server.exception.LeafServerException;
import com.sankuai.inf.leaf.server.exception.LengthZeroException;
import com.sankuai.inf.leaf.server.exception.NoKeyException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class LeafController {
    private Logger logger = LoggerFactory.getLogger(LeafController.class);
    @Autowired
    SegmentService segmentService;
    @Autowired
    SnowflakeService snowflakeService;

    /**
     * Segment获取单个id
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/api/segment/get/{key}")
    public String getSegmentID(@PathVariable("key") String key) {
        return get(key, segmentService.getId(key));
    }

    /**
     * Segment获取多个id
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/api/segment/list/{key}/{length}")
    public List<String> getSegmentID(@PathVariable("key") String key, @PathVariable("length") Integer length) {
        return list(key, segmentService.getIdList(key, length));
    }
    /**
     * snowflake获取单个id
     *
     * @return
     */
    @RequestMapping(value = "/api/snowflake/get")
    public String getSnowflakeID() {
        return get(snowflakeService.getId());
    }

    /**
     * snowflake获取多个id
     * @param length
     * @return
     */
    @RequestMapping(value = "/api/snowflake/list/{length}")
    public List<String> getSnowflakeID(@PathVariable("length") Integer length) {
        return list(length, snowflakeService.getIdList(length));
    }

    private String get(@PathVariable("key") String key, Result id) {
        Result result;
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }

        result = id;
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }
    private String get(Result result) {
        if (result.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(result.toString());
        }
        return String.valueOf(result.getId());
    }

    private List<String> list(String key, ResultList resultList) {
        if (key == null || key.isEmpty()) {
            throw new NoKeyException();
        }
        if (resultList.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(resultList.toString());
        }
        return Lists.transform(resultList.getIdList(), value -> String.valueOf(value));
    }
    private List<String> list(Integer length, ResultList resultList) {
        if (length == null || 0 == length) {
            throw new LengthZeroException();
        }
        if (resultList.getStatus().equals(Status.EXCEPTION)) {
            throw new LeafServerException(resultList.toString());
        }
        return Lists.transform(resultList.getIdList(), value -> String.valueOf(value));
    }
}
