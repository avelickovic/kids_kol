package servent.messeges.mutex;

import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class TokenMessage extends BasicMessage {

    public TokenMessage( ServentInfo originalSenderInfo, ServentInfo receiverInfo) {
        super( MessageType.TOKEN,originalSenderInfo, receiverInfo);
    }
}
