package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class NodeDoneMessage extends BasicMessage {

    public NodeDoneMessage( ServentInfo receiver) {
        super(MessageType.NODE_DONE, AppConfig.myServentInfo, receiver);
    }
}
