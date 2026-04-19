package servent.handlers.factorial;

import app.AppConfig;
import servent.handlers.MessageHandler;
import servent.messeges.Message;
import servent.messeges.factorial.FactorialHelpMessage;
import servent.messeges.factorial.FactorialResultMessage;
import servent.messeges.util.MessageUtil;

import java.math.BigInteger;

public class FactorialHelpHandler implements MessageHandler {
    private Message clientMessage;

    public FactorialHelpHandler(Message clientMessage) {
        this.clientMessage = clientMessage;
    }

    @Override
    public void run() {
        BigInteger number = new BigInteger(clientMessage.getMessageText());
        BigInteger result = BigInteger.ONE;
        for(BigInteger i =BigInteger.ONE;i.compareTo(number)!=1;i=i.add(BigInteger.ONE)){
            result=result.multiply(i);
        }
        MessageUtil.sendMessage(new FactorialResultMessage(AppConfig.myServentInfo,clientMessage.getOriginalSenderInfo(),result.toString()));

    }
}
