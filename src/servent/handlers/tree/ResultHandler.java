package servent.handlers.tree;

import app.AppConfig;
import app.TreeState;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;
import servent.messeges.tree.ResultMessage;
import servent.messeges.util.MessageUtil;

public class ResultHandler implements MessageHandler {
    Message clientMessage;

    public ResultHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if (clientMessage.getMessageType() == MessageType.RESULT) {
            long childResult = Long.parseLong(clientMessage.getMessageText());
            int senderId = clientMessage.getOriginalSenderInfo().getId();

            AppConfig.timestampedStandardPrint("Got result " + childResult + " from " + senderId);

            synchronized (TreeState.treeLock) {
                AppConfig.TREE_STATE.addChildResult(childResult);

                if (AppConfig.TREE_STATE.allChildResultsReceived()) {
                    AppConfig.TREE_STATE.sendResultToParent(); // ✅ iz TreeState
                }
            }
        }
    }

}
