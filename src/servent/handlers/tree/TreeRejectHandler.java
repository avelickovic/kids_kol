package servent.handlers.tree;

import app.AppConfig;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;

public class TreeRejectHandler implements MessageHandler {


    private Message clientMessage;

    public TreeRejectHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType()== MessageType.TREE_REJECT) {
            int senderId=clientMessage.getOriginalSenderInfo().getId();
            AppConfig.TREE_STATE.getUnrelated().add(senderId);
            AppConfig.TREE_STATE.checkNodeLinked();
        }
    }
}
