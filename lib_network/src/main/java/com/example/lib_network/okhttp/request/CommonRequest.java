package com.example.lib_network.okhttp.request;

import java.io.File;
import java.util.Map;

import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by yxm on 2019/12/8
 *
 * @function 封装请求体,对外提供get,post文件上传请求
 */
public class CommonRequest {

    /**
     * 重载方法
     * @param url
     * @param params
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params){
        return createPostRequest(url, params,null);
    }
    /**
     * 对外创建post请求对象
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createPostRequest(String url, RequestParams params, RequestParams headers){
        //请求体
        FormBody.Builder mFormBodyBuilder = new FormBody.Builder();
        if(params != null){
            for (Map.Entry<String,String> entry : params.urlParams.entrySet()){
                mFormBodyBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        //请求头
        Headers.Builder mHeaderBuilder = new Headers.Builder();
        if(headers != null){
            for (Map.Entry<String,String> entry : headers.urlParams.entrySet()){
                mHeaderBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        //构建请求
        return  new Request.Builder()
                .url(url)
                .headers(mHeaderBuilder.build())
                .post(mFormBodyBuilder.build())
                .build();
    }

    /**
     * get请求
     * @param url
     * @param params
     * @param headers
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params, RequestParams headers){
        StringBuilder urlBuilder = new StringBuilder(url).append("?");
        if(params != null){
            for (Map.Entry<String,String> entry : params.urlParams.entrySet()){
                urlBuilder.append(entry.getKey())
                .append("=")
                .append(entry.getValue());
            }
        }
        //请求头
        Headers.Builder mHeaderBuilder = new Headers.Builder();
        if(headers != null){
            for (Map.Entry<String,String> entry : headers.urlParams.entrySet()){
                mHeaderBuilder.add(entry.getKey(),entry.getValue());
            }
        }
        return new Request.Builder()
                .url(urlBuilder.toString())
                .headers(mHeaderBuilder.build())
                .get()
                .build();
    }

    /**
     * 重载get请求方法
     * @param url
     * @param params
     * @return
     */
    public static Request createGetRequest(String url, RequestParams params){
        return createGetRequest(url, params,null);
    }

    public static final MediaType FILE_TYPE =  MediaType.parse("application/octet-stream");
    /**
     * 上传文件请求
     * @param url
     * @param params
     * @return
     */
    public static Request createMultiPostRequest(String url, RequestParams params){
        MultipartBody.Builder multipartBody = new MultipartBody.Builder();
        multipartBody.setType(FILE_TYPE);
        if(params != null){
            for (Map.Entry<String,Object> entry : params.fileParams.entrySet()){
                if(entry.getValue() instanceof File){
                    multipartBody.addPart(Headers.of("content-Disposition","form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(FILE_TYPE,(File) entry.getValue()));
                }else if (entry.getValue() instanceof String){
                    multipartBody.addPart(Headers.of("content-Disposition","form-data; name=\"" + entry.getKey() + "\""),
                            RequestBody.create(null,(String) entry.getValue()));
                }
            }
        }
        return new Request.Builder()
                .url(url)
                .post(multipartBody.build())
                .build();
    }
}
