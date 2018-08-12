package cn.net.polyglot.net;

import cn.net.polyglot.config.Constants;
import cn.net.polyglot.util.Util;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AppService{

    private NetClient netClient;
    private NetSocket clientSocket;
    private MessageReceiverHandler messageReceiverHandler;
    private Thread messageEvent;

    private AppService(){
        messageReceiverHandler=new MessageReceiverHandler();
        NetClientOptions options = new NetClientOptions().setConnectTimeout(10000);
        netClient=Vertx.vertx().createNetClient(options);
        messageEvent=new Thread(new MessageEvent(messageReceiverHandler.buffers),"messageEvent");
        messageEvent.setDaemon(true);
        messageEvent.start();
    }

    private static class INSTANCE{
        private static AppService instance=new AppService();
    }

    public static AppService get(){
        return INSTANCE.instance;
    }

    public void doLogin(String account,String psd){

        netClient.connect(Constants.DEFAULT_TCP_PORT, Constants.SERVER, event -> {
            if (event.succeeded()) {

                clientSocket = event.result();
                clientSocket.exceptionHandler(new ExceptionHandler());
                clientSocket.closeHandler(v->clientSocket=null);
                clientSocket.handler(new MessageReceiverHandler());
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

    public void loginOut(){
        //todo： 做退出登陆操作
        disconnect();
    }
    public void disconnect(){
        if(clientSocket!=null){
            clientSocket.close();
        }
        netClient.close();
    }

    public void sendMessage(JsonObject msg){
        if(clientSocket!=null){
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
    private static final class MessageReceiverHandler implements Handler<Buffer>{

        private BlockingQueue<Buffer> buffers;
        private Buffer tempBuffer;

        public MessageReceiverHandler() {
            this.buffers = new LinkedBlockingQueue<>();
            tempBuffer=Buffer.buffer();
        }

        @Override
        public void handle(Buffer buffer) {
            // 处理socket发送过来的消息
            String key;
            for (int start=0;start<buffer.length();start++){
                byte b=buffer.getByte(start);
                int end=start+1;
                if(end<=buffer.length()){

                }

            }
        }

        public BlockingQueue<Buffer> getBuffers() {
            return buffers;
        }
    }

    /**
     * 处理消息分发
     */
    private static final class MessageEvent implements Runnable{

        private BlockingQueue<Buffer> queue;

        public MessageEvent(BlockingQueue<Buffer> queue) {
            this.queue = queue;
        }

        @Override
        public void run() {
            while (true){
                try {
                    Buffer buffer=queue.take();
                    //todo
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
