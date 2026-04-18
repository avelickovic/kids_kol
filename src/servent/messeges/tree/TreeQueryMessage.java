package servent.messeges.tree;

import app.AppConfig;
import app.ServentInfo;
import servent.messeges.BasicMessage;
import servent.messeges.MessageType;

public class TreeQueryMessage extends BasicMessage {
        private static final long serialVersionUID = 123456789L;


    public TreeQueryMessage( ServentInfo receiver) {
        super(MessageType.TREE_QUERY, AppConfig.myServentInfo, receiver);
    }
}
