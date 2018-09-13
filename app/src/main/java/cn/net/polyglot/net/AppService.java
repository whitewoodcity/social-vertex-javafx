package cn.net.polyglot.net;

import cn.net.polyglot.common.DataManager;
import cn.net.polyglot.config.Constants;
import cn.net.polyglot.controller.entity.Contact;
import cn.net.polyglot.util.Util;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;
import javafx.application.Platform;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.function.Consumer;

public class AppService {

    private Vertx vertx;
    private NetClient netClient;
    private NetSocket clientSocket;
    private MessageReceiverHandler messageReceiverHandler;
    private Thread messageEvent;
    private Consumer<Boolean> loginCallback;

    private AppService() {
        messageReceiverHandler = new MessageReceiverHandler();
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        vertx=Vertx.vertx();
        netClient = vertx.createNetClient(options);

    }

    private static class INSTANCE {
        private static AppService instance = new AppService();
    }

    public static AppService get() {
        return INSTANCE.instance;
    }

    public void doLogin(String account, String psd, Consumer<Boolean> consumer) {
        this.loginCallback=consumer;
        messageEvent = new Thread(new MessageEvent(messageReceiverHandler.buffers), "messageEvent");
        messageEvent.setDaemon(true);
        messageEvent.start();
        netClient.connect(Constants.DEFAULT_TCP_PORT, Constants.SERVER, event -> {
            if (event.succeeded()) {

                clientSocket = event.result();
                clientSocket.exceptionHandler(new ExceptionHandler());
                clientSocket.closeHandler(v -> clientSocket = null);
                clientSocket.handler(messageReceiverHandler);
                Map<String, Object> map = new HashMap<>();
                map.put(Constants.TYPE, "user");
                map.put(Constants.SUBTYPE, "login");
                map.put(Constants.ID, account);
                map.put(Constants.PASSWORD, Util.md5(psd));
                map.put(Constants.VERSION, Constants.CURRENT_VERSION);
                JsonObject jsonObject = new JsonObject(map);
                clientSocket.write(jsonObject.toString());
                clientSocket.write("\r\n");
            } else if (event.failed()) {
                event.cause().printStackTrace();
            }
        });
    }

    public void loginOut() {
        //todo： 做退出登陆操作
        disconnect();
    }

    public void disconnect() {
        if (clientSocket != null) {
            clientSocket.close();
        }
        netClient.close();
        vertx.close();
        if(messageEvent!=null&&messageEvent.isAlive()){
            messageEvent.interrupt();
            messageEvent=null;
        }
    }

    public void sendMessage(JsonObject msg) {
        if (clientSocket != null) {
            clientSocket.write(msg.toString());
            clientSocket.write("\r\n");
        }
    }


    /**
     * 异常
     */
    private static final class ExceptionHandler implements Handler<Throwable> {
        @Override
        public void handle(Throwable event) {
            System.out.println("出现异常了");
            event.printStackTrace();
            System.out.println("========end========");
        }
    }

    /**
     * 接受消息handler
     */
    private static final class MessageReceiverHandler implements Handler<Buffer> {

        private BlockingQueue<String> buffers;
        private String tmp = "";

        public MessageReceiverHandler() {
            this.buffers = new LinkedBlockingQueue<>();
        }

        @Override
        public void handle(Buffer buffer) {
            // 处理socket发送过来的消息
            tmp += buffer.toString();
            int index = tmp.indexOf("\r\n");
            if (index != -1) {
                String data = tmp.substring(0, index);
                tmp = tmp.substring(index + 2);
                System.out.println("接受=" + data);
                buffers.offer(data);

            }
        }

        public BlockingQueue<String> getBuffers() {
            return buffers;
        }
    }

    /**
     * 处理消息分发
     */
    private  final class MessageEvent implements Runnable {

        private BlockingQueue<String> queue;

        public MessageEvent(BlockingQueue<String> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    String buffer = queue.take();
                    //todo
                    JsonObject jsonObject=new JsonObject(buffer);
                    String type=jsonObject.getString(Constants.TYPE,null);
                    String subtype=jsonObject.getString(Constants.SUBTYPE,null);
                    if("user".equals(type)&&"login".equals(subtype)&&loginCallback!=null){
                        Boolean login=jsonObject.getBoolean("login",false);
                        JsonArray jsonArray=jsonObject.getJsonArray("friends");
                        for (int i=0;i<jsonArray.size();i++){
                            JsonObject jo=jsonArray.getJsonObject(i);
                            Contact contact=new Contact();
                            contact.setId(jo.getString("id",null));
                            contact.setNickName(jo.getString("nickname",null));
                            DataManager.addContact(contact);
                        }
                        Contact contact=new Contact();
                        contact.setNickName("ggx2018");
                        contact.setId("ggx2018");
                        DataManager.addContact(contact);
                        Platform.runLater(()-> loginCallback.accept(login));
                    }
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
