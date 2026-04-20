package servent.handlers.mutex;

import app.AppConfig;
import mutex.DistributedMutex;
import mutex.LamportMutex;
import mutex.MutexType;
import servent.handlers.MessageHandler;
import servent.messeges.Message;

public class LamportReleaseHandler implements MessageHandler {

    private Message clientMessage;
    private LamportMutex mutex;

    public LamportReleaseHandler(Message clientMessage, DistributedMutex mutex) {

        if(mutex instanceof LamportMutex){
            this.mutex = (LamportMutex) mutex;
        }
        else{
            AppConfig.timestampedErrorPrint("Handling lamport release message in non-lamport mutex: ");
        }
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        long messageTimeStamp = Long.parseLong(clientMessage.getMessageText());
        mutex.updateTimeStamp(messageTimeStamp);
        mutex.removeHeadOfQueue();
    }
}
