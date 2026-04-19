package servent;

import app.AppConfig;
import app.Cancellable;
import servent.handlers.*;
import servent.handlers.tree.*;
import servent.messeges.Message;
import servent.messeges.util.MessageUtil;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleServentListener implements Runnable, Cancellable {

    private volatile boolean working = true;

    /*
     * Thread pool for executing the handlers. Each client will get it's own handler thread.
     */
    private final ExecutorService threadPool =
            Executors.newWorkStealingPool();

    @Override
    public void run() {
        ServerSocket listenerSocket = null;
        try {
            listenerSocket = new ServerSocket(AppConfig.myServentInfo.getListenerPort());
            /*
             * If there is no connection after 1s, wake up and see if we should terminate.
             */
            listenerSocket.setSoTimeout(1000);
        } catch (IOException e) {
            AppConfig.timestampedErrorPrint("Couldn't open listener socket on: " + AppConfig.myServentInfo.getListenerPort());
            System.exit(0);
        }

        while (working) {
            try {
                /*
                 * This blocks for up to 1s, after which SocketTimeoutException is thrown.
                 */
                Socket clientSocket = listenerSocket.accept();

                //GOT A MESSAGE! <3
                Message clientMessage = MessageUtil.readMessage(clientSocket);
                MessageHandler messageHandler = new NullHandler(clientMessage);

                /*
                 * Each message type has it's own handler.
                 * If we can get away with stateless handlers, we will,
                 * because that way is much simpler and less error prone.
                 */
                switch (clientMessage.getMessageType()) {
                    case PING:
                        messageHandler = new PingHandler(clientMessage);
                        break;
                    case PONG:
                        messageHandler = new PongHandler(clientMessage);
                        break;
                    case TREE_ACCEPT:
                        messageHandler = new TreeAcceptHandler(clientMessage);
                        break;
                    case TREE_REJECT:
                        messageHandler = new TreeRejectHandler(clientMessage);
                        break;
                    case TREE_QUERY:
                        messageHandler = new TreeQueryHandler(clientMessage);
                        break;
                    case NODE_DONE:
                        messageHandler = new NodeDoneHandler(clientMessage);
                        break;
                    case RESULT:
                        messageHandler = new ResultHandler(clientMessage);
                        break;
                    case RANGE:
                        messageHandler = new RangeHandler(clientMessage);
                        break;
                    case FACTORIEL_REQUEST:
                        messageHandler = new FactorielRequsetHandler(clientMessage);
                        break;
                }

                threadPool.submit(messageHandler);
            } catch (SocketTimeoutException timeoutEx) {
                //Uncomment the next line to see that we are waking up every second.
//				AppConfig.timedStandardPrint("Waiting...");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop() {
        this.working = false;
    }
}
