package servent.handlers.tree;

import app.AppConfig;
import app.TreeState;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;
import servent.messeges.tree.NodeDoneMessage;
import servent.messeges.util.MessageUtil;

public class TreeAcceptHandler implements MessageHandler {

    private Message clientMessage;

    public TreeAcceptHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType()== MessageType.TREE_ACCEPT){
            int senderId=clientMessage.getOriginalSenderInfo().getId();
            AppConfig.TREE_STATE.getChildren().add(senderId);
            AppConfig.TREE_STATE.checkNodeLinked();

        }
    }
}
