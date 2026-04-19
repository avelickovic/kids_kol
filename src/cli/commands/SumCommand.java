package cli.commands;

import app.AppConfig;
import app.FactorialShared;
import app.ServentInfo;
import servent.messeges.factorial.FactorialHelpMessage;
import servent.messeges.util.MessageUtil;

import java.math.BigInteger;

public class SumCommand implements CLICommand {
    @Override
    public String commandName() {
        return "sum";
    }

    @Override
    public void execute(String args) {
        BigInteger number=new BigInteger(args);
        int myId= AppConfig.myServentInfo.getId();
        int friendsId;
        if(myId%2==0){
            friendsId=(myId+1)%AppConfig.getServentCount();
        }
        else{
            friendsId=myId-1;
        }
        ServentInfo friendInfo=AppConfig.getInfoById(friendsId);
        BigInteger friendsHalf =number.divide(BigInteger.TWO);
        AppConfig.timestampedStandardPrint("Asking my friend " + friendInfo.getId() + " to calculate factorial of " + friendsHalf);
        BigInteger myHalf=number.subtract(friendsHalf);
        AppConfig.timestampedStandardPrint("I will calculate factorial of " + myHalf);
        MessageUtil.sendMessage(new FactorialHelpMessage(AppConfig.myServentInfo,friendInfo,friendsHalf.toString()));
        BigInteger myResult=BigInteger.ONE;
        for(BigInteger i=myHalf;i.compareTo(number)!=1;i=i.add(BigInteger.ONE)){
            myResult=myResult.multiply(i);
        }
        AppConfig.timestampedStandardPrint("My result is: " + myResult);
        while (FactorialShared.friendsHalf.get()==null){

        }
        BigInteger result=FactorialShared.friendsHalf.get().multiply(myResult);
        AppConfig.timestampedStandardPrint("Factorial of " +args + " is: " + result);

    }
}
