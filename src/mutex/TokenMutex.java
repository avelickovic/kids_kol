package mutex;

import app.AppConfig;
import servent.messeges.mutex.TokenMessage;
import servent.messeges.util.MessageUtil;

public class TokenMutex implements DistributedMutex {
    private volatile boolean hasToken;
    private volatile boolean wantsLock;

    @Override
    public void lock() {
        wantsLock = true;
        while(!hasToken){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void unlock() {
        wantsLock = false;
        hasToken = false;
        sendTokenForward();
    }
    public void sendTokenForward() {
        int nextNodeId = (AppConfig.myServentInfo.getId() + 1) % AppConfig.getServentCount();
        MessageUtil.sendMessage(new TokenMessage(AppConfig.myServentInfo, AppConfig.getInfoById(nextNodeId)));

    }

    public void receiveToken(){
        if(wantsLock){
            hasToken=true;
        }
        else{
            sendTokenForward();
        }
    }
}
