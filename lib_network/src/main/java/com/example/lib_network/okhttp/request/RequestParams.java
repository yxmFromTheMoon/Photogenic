package com.example.lib_network.okhttp.request;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 请求参数
 */
public class RequestParams {
    public ConcurrentHashMap<String,String> urlParams = new ConcurrentHashMap<>();
    public ConcurrentHashMap<String,Object> fileParams = new ConcurrentHashMap<>();

    public RequestParams(){
        this((Map<String,String>) null);
    }

    public RequestParams(Map<String,String> source){
        if(source != null){
            for (Map.Entry<String,String> entry : source.entrySet()){
                put(entry.getKey(), entry.getValue());
            }
        }
    }

    public RequestParams(final String key, final String value){
        this(new HashMap<String, String>(){
            {
                put(key, value);
            }
        });
    }

    /**
     * Adds a key/value string pair to the request.
     *
     * @param key   the key name for the new param.
     * @param value the value string for the new param.
     */
    public void put(String key,String value){
        if(key != null && value != null){
            urlParams.put(key, value);
        }
    }

    public void put(String key, Object object) throws FileNotFoundException {
        if (key != null) {
            fileParams.put(key, object);
        }
    }

    public boolean hasParams() {
        return urlParams.size() > 0 || fileParams.size() > 0;
    }

}
