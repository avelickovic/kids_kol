package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.Message;
import servent.messeges.MessageType;

public class TreeAcceptMessage extends BasicMessage {

    private static final long serialVersionUID = 123456789L;

    public TreeAcceptMessage(ServentInfo receiver) {
        super(MessageType.TREE_ACCEPT, AppConfig.myServentInfo, receiver);
    }
}
