package servent.handlers.tree;

import app.AppConfig;
import app.TreeState;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;

public class NodeDoneHandler implements MessageHandler {

    private Message clientMessage;

    public NodeDoneHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.NODE_DONE) {
            int senderId = clientMessage.getOriginalSenderInfo().getId();
            AppConfig.timestampedStandardPrint("Got NODE_DONE from " + senderId);

            synchronized (TreeState.treeLock) {
                AppConfig.TREE_STATE.addDoneChild(senderId);
                AppConfig.TREE_STATE.checkAndNotifyParent();
            }
        }
    }
}
