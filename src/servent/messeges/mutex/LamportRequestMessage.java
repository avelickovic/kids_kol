package servent.messeges.mutex;

import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class LamportRequestMessage extends BasicMessage {

    public LamportRequestMessage( ServentInfo originalSenderInfo, ServentInfo receiverInfo,long timeStamp ) {
        super(MessageType.LAMPORT_REQUEST, originalSenderInfo, receiverInfo, String.valueOf(timeStamp));
    }
}
