package servent.handlers.mutex;

import app.AppConfig;
import mutex.DistributedMutex;
import mutex.MutexType;
import mutex.TokenMutex;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;

public class TokenHandler implements MessageHandler {


    private Message clientMessage;
    private TokenMutex mutex;

    public TokenHandler(Message clientMessage, DistributedMutex mutex) {
        this.clientMessage = clientMessage;
        if(AppConfig.MUTEX_TYPE != MutexType.TOKEN) {
            throw new IllegalArgumentException("TokenHandler can only be used with TokenMutex");
        }
        else {
            this.mutex= (TokenMutex)mutex;
        }
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType() == MessageType.TOKEN) {
            mutex.receiveToken();
        }
        else {
            AppConfig.timestampedErrorPrint("Received non-token message in TokenHandler: " + clientMessage);
        }
    }
}
