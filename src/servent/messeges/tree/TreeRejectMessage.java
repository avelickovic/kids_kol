package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class TreeRejectMessage extends BasicMessage {


    private static final long serialVersionUID = 123456789L;
    public TreeRejectMessage (ServentInfo receiver) {
        super(MessageType.TREE_REJECT, AppConfig.myServentInfo, receiver);
    }
}
