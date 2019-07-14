package cn.net.polyglot.net;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.NetClient;
import io.vertx.core.net.NetClientOptions;
import io.vertx.core.net.NetSocket;

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
                } catch (InterruptedException e) {
                    break;
                }
            }
        }
    }
}
