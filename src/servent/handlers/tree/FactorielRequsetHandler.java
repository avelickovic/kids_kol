package servent.handlers.tree;

import app.AppConfig;
import cli.commands.FactorielCommand;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.MessageType;
import servent.messeges.tree.FactorielRequestMessage;
import servent.messeges.util.MessageUtil;

public class FactorielRequsetHandler implements MessageHandler {
    Message clientMessage;

    public FactorielRequsetHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        if(clientMessage.getMessageType()== MessageType.FACTORIEL_REQUEST){
           String number=clientMessage.getMessageText();
           if(AppConfig.TREE_STATE.getParentId()==AppConfig.myServentInfo.getId()){
               if(!AppConfig.TREE_STATE.isFinalTree()) {
                   AppConfig.timestampedStandardPrint("Tree is not final, cannot process request");
                   return;
               }
               AppConfig.timestampedStandardPrint("Root received factorial request for: " + number);
               new FactorielCommand().execute(number);
           }
           else{
              int parentId=AppConfig.TREE_STATE.getParentId();
              AppConfig.timestampedStandardPrint("Forwarding factorial request for " + number + " to parent " + parentId);
               MessageUtil.sendMessage(new FactorielRequestMessage(AppConfig.getInfoById(parentId), number));
           }
        }
    }
}
