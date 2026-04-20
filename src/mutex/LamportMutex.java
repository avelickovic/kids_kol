package mutex;

import app.AppConfig;
import servent.messeges.mutex.LamportRequestMessage;
import servent.messeges.util.MessageUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class LamportMutex implements DistributedMutex{

    private AtomicBoolean distributedMutexInitiated = new AtomicBoolean(false);

    private AtomicLong timeStamp=null;

    public AtomicInteger replyCount = new AtomicInteger(0);

    private BlockingQueue<LamportRequestItem> requestQueue;

    public LamportMutex(){
        timeStamp = new AtomicLong(AppConfig.myServentInfo.getId());

        requestQueue = new LinkedBlockingQueue<>();
    }

    public Long getTimeStamp() {
        return timeStamp.get();
    }

    public void addToQueue(LamportRequestItem item){
        try {
            requestQueue.put(item);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void updateTimeStamp(long newTimeStamp){
        long currentTimeStamp = timeStamp.get();

        while(newTimeStamp > currentTimeStamp){
            if(timeStamp.compareAndSet(currentTimeStamp, newTimeStamp+1)){
                break;
            }
            currentTimeStamp = timeStamp.get();
        }
    }

    public void incrementReplyCount() {
        replyCount.getAndIncrement();
    }

    public void removeHeadOfQueue(){
        try {
            requestQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void lock() {
        while (distributedMutexInitiated.compareAndSet(false, true)) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (int i = 0; i < AppConfig.myServentInfo.getNeighbors().size(); i++) {
            int neighborId = AppConfig.myServentInfo.getNeighbors().get(i);

            MessageUtil.sendMessage(new LamportRequestMessage(AppConfig.myServentInfo,
                    AppConfig.getInfoById(neighborId), timeStamp.get()));
        }
        addToQueue(new LamportRequestItem(timeStamp.get(),AppConfig.myServentInfo.getId()));
        while (true){
            if(replyCount.get()==AppConfig.getServentCount()-1 && requestQueue.peek().getId() == AppConfig.myServentInfo.getId()){
                break;
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void unlock() {
        removeHeadOfQueue();
        replyCount.set(0);
            for(int i = 0; i<AppConfig.myServentInfo.getNeighbors().size(); i++){
                int neighborId = AppConfig.myServentInfo.getNeighbors().get(i);

                MessageUtil.sendMessage(new LamportRequestMessage(AppConfig.myServentInfo,
                        AppConfig.getInfoById(neighborId), timeStamp.get()));
            }
        distributedMutexInitiated.set(false);
    }
}
