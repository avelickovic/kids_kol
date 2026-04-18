package servent.handlers.tree;

import app.AppConfig;
import app.TreeState;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;
import servent.messeges.tree.TreeAcceptMessage;
import servent.messeges.tree.TreeQueryMessage;
import servent.messeges.tree.TreeRejectMessage;
import servent.messeges.util.MessageUtil;

public class TreeQueryHandler implements MessageHandler {

    private Message clientMessage;

    public TreeQueryHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType()== MessageType.TREE_QUERY) {
            int senderId=clientMessage.getOriginalSenderInfo().getId();
            synchronized (TreeState.treeLock){
                if(AppConfig.TREE_STATE.getParentId()==-1){
                    AppConfig.timestampedStandardPrint("Sending accept to "+senderId);
                    MessageUtil.sendMessage(new TreeAcceptMessage(clientMessage.getOriginalSenderInfo()));
                    AppConfig.TREE_STATE.setParentId(senderId);
                    for(Integer neighbor: AppConfig.myServentInfo.getNeighbors()){
                        if(neighbor!=senderId){
                            MessageUtil.sendMessage(new TreeQueryMessage(AppConfig.getInfoById(neighbor)));
                        }
                    }
                    AppConfig.TREE_STATE.checkNodeLinked();
                }
                else{
                    AppConfig.timestampedStandardPrint("Already got parent "+AppConfig.TREE_STATE.getParentId()+"sending reject to" +senderId);
                    MessageUtil.sendMessage(new TreeRejectMessage(clientMessage.getOriginalSenderInfo()));
                }
            }
        }
    }
}
