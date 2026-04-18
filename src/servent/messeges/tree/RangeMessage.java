package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.Message;
import servent.messeges.MessageType;

public class RangeMessage extends BasicMessage {

    public RangeMessage( ServentInfo receiver, String messageText) {
        super(MessageType.RANGE, AppConfig.myServentInfo, receiver, messageText);
    }
}
