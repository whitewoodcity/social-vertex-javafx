package cn.net.polyglot.net;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.util.Util;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.codec.BodyCodec;
import javafx.application.Platform;

import java.util.function.Consumer;

public class HttpService {

    private static final String HTTP_HOST = Constants.SERVER;
    private static final int PORT = Constants.DEFAULT_HTTP_PORT;
    private static HttpService service;

    private Vertx vertx;
    private WebClient webClient;

    private HttpService() {
        vertx = Vertx.vertx();
        webClient = WebClient.create(vertx);
    }

    public static HttpService get() {
        if (service == null) {
            synchronized (HttpService.class) {
                if (service == null) {
                    service = new HttpService();
                }
            }
        }
        return service;
    }

    public void close() {
        if (webClient != null) {
            webClient.close();
            webClient = null;
        }
        if (vertx != null) {
            vertx.close();
            vertx = null;
        }
        service = null;
    }

    public void put(JsonObject jsonObject, Consumer<HttpResponse<JsonObject>> success, Consumer<AsyncResult<HttpResponse<JsonObject>>> fail) {
        put("", jsonObject, success, fail);
    }

    public void put(String requestUri, JsonObject jsonObject, Consumer<HttpResponse<JsonObject>> success, Consumer<AsyncResult<HttpResponse<JsonObject>>> fail) {
        if (webClient == null) {
            return;
        }
        webClient.put(PORT, HTTP_HOST, requestUri)
                .timeout(30000)
                .as(BodyCodec.jsonObject())
                .sendJsonObject(jsonObject, ar -> {
                    if (ar.succeeded()) {
                        if (success != null) success.accept(ar.result());
                    } else {
                        if (fail != null) fail.accept(ar);
                    }
                });
    }

    public void doLogin(String account,String password,
                        Consumer<HttpResponse<JsonObject>> success,
                        Runnable fail){
       JsonObject jo= new JsonObject()
                .put(Constants.TYPE, Constants.USER)
                .put(Constants.SUBTYPE, Constants.LOGIN)
                .put(Constants.ID, account)
                .put(Constants.PASSWORD, Util.md5(password))
                .put(Constants.VERSION, Constants.CURRENT_VERSION);
       if(webClient==null){
           return;
       }
       webClient.put(PORT,HTTP_HOST,"")
               .timeout(30000)
               .as(BodyCodec.jsonObject())
               .sendJsonObject(jo,ar->{
                   if(ar.succeeded()){
                       if(success!=null){
                           Platform.runLater(()-> success.accept(ar.result()));
                       }
                   }else {
                       ar.cause().printStackTrace();
                       if(fail!=null){
                           Platform.runLater(fail);
                       }
                   }
               });

    }

}
