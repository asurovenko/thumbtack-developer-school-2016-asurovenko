package net.asurovenko.netexam.di;

import android.support.annotation.NonNull;

import com.squareup.otto.Bus;

import net.asurovenko.netexam.app.App;
import net.asurovenko.netexam.app.Preferences;
import net.asurovenko.netexam.network.NetExamApi;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


@Module
public class AppModule {
    //access to the localhost from genymotion with 10.0.3.2
    private static final String BASE_URL = "http://82.200.2.44:8888";

    private final App app;

    public AppModule(App app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Bus provideBus() {
        return new Bus();
    }

    @Provides
    @Singleton
    public Preferences providePreferences() {
        return new Preferences(app);
    }

    @Provides
    @Singleton
    public NetExamApi provideApi() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(getInterceptor())
                .addInterceptor(logging)
                .build();

        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(httpClient)
                .build()
                .create(NetExamApi.class);
    }

    @NonNull
    private Interceptor getInterceptor() {
        return chain -> {
            Request request = chain.request().newBuilder()
                    .addHeader("Accept", "application/json")
                    .build();
            return chain.proceed(request);
        };
    }
}