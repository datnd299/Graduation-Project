package com.example.signboard2.rest;

import android.util.Log;

import com.example.signboard2.utils.Constant;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.charset.Charset;

import okhttp3.CacheControl;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;

import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.BufferedSink;

public  class RequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        HttpUrl url = chain.request().url().newBuilder()

                .build();
//        AddPostParamRequestBody newBody = new AddPostParamRequestBody(request.body(), "token", Constant.TOKEN);

        Request newRequest = request.newBuilder()
                .method(request.method(),request.body())
                .addHeader("Authorization", "Bearer "+Constant.TOKEN)
                .url(url)
                .build();
        Response response = chain.proceed(newRequest);




        return response;

    }
}
class AddPostParamRequestBody extends RequestBody {

    final RequestBody body;
    final String parameter;

    AddPostParamRequestBody(RequestBody body, String name, String value) {
        this.body = body;
        this.parameter = "&" + name + "=" + value;
    }

    @Override
    public long contentLength() throws IOException {
        return body.contentLength() + parameter.length();
    }

    @Override
    public MediaType contentType() {
        return body.contentType();
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        body.writeTo(bufferedSink);
        bufferedSink.writeString(parameter, Charset.forName("UTF-8"));
    }

}