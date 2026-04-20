package servent.messeges.mutex;

import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class LamportReleaseMessage extends BasicMessage {

    public LamportReleaseMessage(ServentInfo originalSenderInfo, ServentInfo receiverInfo, long timeStamp) {
        super(MessageType.LAMPORT_RELEASE, originalSenderInfo, receiverInfo,String.valueOf(timeStamp));
    }
}
