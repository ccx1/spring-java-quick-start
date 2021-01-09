package com.ccx.config.socket;

import com.alibaba.fastjson.JSON;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class ServerEncoder implements Encoder.Text<Object> {


    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(Object o) {
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof Integer) {
            return String.valueOf(o);
        } else if (o instanceof Float) {
            return String.valueOf(o);
        } else if (o instanceof Long) {
            return String.valueOf(o);
        } else if (o instanceof Boolean) {
            return String.valueOf(o);
        } else {
            return JSON.toJSONString(o);
        }
    }

}
