package servent.handlers.mutex;

import mutex.DistributedMutex;
import mutex.LamportMutex;
import servent.handlers.MessageHandler;
import servent.messeges.Message;

public class LamportReplyHandler implements MessageHandler {

    private LamportMutex mutex;
    private Message clientMessage;

    public LamportReplyHandler(Message clientMessage, DistributedMutex mutex) {
        if(mutex instanceof LamportMutex) {
            this.mutex = (LamportMutex) mutex;
        }
        else{
            System.out.println("Handling lamport reply message in non-lamport mutex: ");
        }

        this.clientMessage = clientMessage;

    }

    @Override
    public void run() {
        long messageTimeStamp = Long.parseLong(clientMessage.getMessageText());
        mutex.updateTimeStamp(messageTimeStamp);
        mutex.incrementReplyCount();
    }
}
