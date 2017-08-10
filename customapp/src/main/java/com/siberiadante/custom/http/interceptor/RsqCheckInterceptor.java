package com.siberiadante.custom.http.interceptor;


import com.siberiadante.custom.util.net.InterceptorUtil;

import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @Created SiberiaDante
 * @Describe：
 * @Time: 2017/8/1
 * @Email: 994537867@qq.com
 * @GitHub: https://github.com/SiberiaDante
 */

public class RsqCheckInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        final Response response = chain.proceed(chain.request());
        final ResponseBody responseBody = response.body();
        try {
            final JSONObject jsonObject = new JSONObject(InterceptorUtil.getRspData(responseBody));
            final int code = jsonObject.getInt("code");
            if (code < 200 || code > 300) {
                throw new IOException(jsonObject.getString("info"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("parase data error");
        }
        return response;
    }
}
