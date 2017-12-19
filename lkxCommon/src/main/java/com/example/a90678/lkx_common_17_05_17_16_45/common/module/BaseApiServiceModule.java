package com.example.a90678.lkx_common_17_05_17_16_45.common.module;

import com.example.a90678.lkx_common_17_05_17_16_45.common.data.BaseApiService;
import com.example.a90678.lkx_common_17_05_17_16_45.common.download.DownloadInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import dagger.Module;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Arron on 16/7/26.
 */
@Module
public class BaseApiServiceModule {

//    public static final String API_SERVER_URL = "http://192.168.1.34:5555";
//public static final String API_SERVER_URL = "http://192.168.1.31:85";
//public static final String API_SERVER_URL = "http://192.168.1.10";
//public static final String API_SERVER_URL = "http://oa.isoffice.cn/";
//public static final String WEB_ROOT = "http://192.168.3.4:8080/";
//public static final String WEB_ROOT = "http://119.29.241.121:8080/";

//public static final String API_SERVER_URL = "http://192.168.3.4:8080/Web_17_05_27_17_15/";
//public static final String API_SERVER_URL = WEB_ROOT+"Web_17_06_14_15_01/";

    private static OkHttpClient.Builder mBuilder;
    private static final HashMap<String, List<Cookie>> cookieStore = new HashMap<>();

    private String webRoot;

    private static boolean isDownloadInterceptor;

    private static DownloadInterceptor downloadInterceptor;

//    @Provides
//    @Singleton
//    protected OkHttpClient provideOkHttpClient() {
//        return new OkHttpClient.Builder().connectTimeout(5000, TimeUnit.MILLISECONDS)
//                .readTimeout(5000, TimeUnit.MILLISECONDS).addInterceptor(new HttpLoggingInterceptor().setLevel(
//                        HttpLoggingInterceptor.Level.BODY)).build();
//    }

    public static String getWebRoot() {
//        if (BuildConfig.DEBUG) {
//            return "http://192.168.3.4:8080/";
//        } else {
//            return  "http://119.29.241.121:8080/";
//        }
//        return "http://192.168.3.4:8080/";
        return  "http://119.29.241.121:8080/";
    }

    public static String getServlet () {
//        return "Web_17_06_14_15_01/";
        return "Web_17_06_30_16_33/";
    }

    public static String getWebRootUrlWithServlet() {
        return getWebRoot()+getServlet();
    }

    protected static RxJavaCallAdapterFactory provideRxJavaCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    protected static GsonConverterFactory provideGsonConverterFactory() {
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
        return GsonConverterFactory.create(gson);
    }

    protected static OkHttpClient.Builder provideOkHttpBuilder() {
        if (mBuilder == null) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            mBuilder = new OkHttpClient.Builder()
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS)
                    .retryOnConnectionFailure(true)//错误重连
                    .addInterceptor(new HttpLoggingInterceptor().setLevel(
                            HttpLoggingInterceptor.Level.BODY))
                    .cookieJar(new CookieJar() {
                        //MD,終於把Cookie帶過去了，這裡key必須是String啊！！！！！！！！！！！！

                        @Override
                        public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                            cookieStore.put(url.host(), cookies);
                        }

                        @Override
                        public List<Cookie> loadForRequest(HttpUrl url) {
                            List<Cookie> cookies = cookieStore.get(url.host());
                            return cookies != null ? cookies : new ArrayList<Cookie>();
                        }
                    });

            return mBuilder;

        } else {
            return mBuilder;
        }
    }

    public static OkHttpClient provideOkHttpClient() {
        OkHttpClient okHttpClient;
            OkHttpClient.Builder builder = provideOkHttpBuilder();
            okHttpClient = builder.build();
        return okHttpClient;
    }

    protected static Retrofit.Builder provideRetrofitBuilder() {

        RxJavaCallAdapterFactory rxJavaCallAdapterFactory = provideRxJavaCallAdapterFactory();
        GsonConverterFactory gsonConverterFactory = provideGsonConverterFactory();
        OkHttpClient client = provideOkHttpClient();
        return new Retrofit.Builder()
                .addCallAdapterFactory(rxJavaCallAdapterFactory)
                .addConverterFactory(gsonConverterFactory)
                .client(client)
                .baseUrl(getWebRootUrlWithServlet());
    }

    public static Retrofit provideRetrofit() {
        Retrofit.Builder builder = provideRetrofitBuilder();
        return builder.build();
    }

    protected BaseApiService provideApiService() {
        Retrofit build = provideRetrofit();
        return build.create(BaseApiService.class);
    }

}
