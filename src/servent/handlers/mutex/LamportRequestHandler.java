package servent.handlers.mutex;

import mutex.DistributedMutex;
import mutex.LamportMutex;
import mutex.LamportRequestItem;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.mutex.LamportReplyMessage;
import servent.messeges.util.MessageUtil;

public class LamportRequestHandler implements MessageHandler {

    private Message clientMessage;
    private LamportMutex mutex;

    public LamportRequestHandler(Message clientMessage, DistributedMutex mutex) {
        this.clientMessage = clientMessage;
        if(mutex instanceof LamportMutex) {
            this.mutex = (LamportMutex) mutex;
        }
        else{
            System.out.println("Handling lamport request message in non-lamport mutex: ");
        }
    }

    @Override
    public void run() {
        long messageTimeStamp = Long.parseLong(clientMessage.getMessageText());
        mutex.updateTimeStamp(messageTimeStamp);

        mutex.addToQueue(new LamportRequestItem(messageTimeStamp, clientMessage.getOriginalSenderInfo().getId()));

        MessageUtil.sendMessage(new LamportReplyMessage(
                clientMessage.getReceiverInfo(),
                clientMessage.getOriginalSenderInfo(),
                mutex.getTimeStamp()));
    }
}
