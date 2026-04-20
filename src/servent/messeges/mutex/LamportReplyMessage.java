package servent.messeges.mutex;

import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class LamportReplyMessage extends BasicMessage {
    public LamportReplyMessage(ServentInfo originalSenderInfo, ServentInfo receiverInfo, long timeStamp ) {
        super(MessageType.LAMPORT_REPLY, originalSenderInfo, receiverInfo, String.valueOf(timeStamp));
    }
}
