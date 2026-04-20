package mutex;

import app.ServentInfo;
import servent.messeges.BasicMessage;

public class LamportRequestItem implements Comparable<LamportRequestItem> {


    private final int id;
    private final long timeStamp;

    public LamportRequestItem(long timeStamp,int id) {
        this.id = id;
        this.timeStamp = timeStamp;
    }

    @Override
    public int compareTo(LamportRequestItem o) {
        if(this.timeStamp == o.timeStamp){
            return this.id - o.id;
        }
        return (int) (this.timeStamp - o.getTimeStamp());
    }

    public int getId() {
        return id;
    }

    public long getTimeStamp() {
        return timeStamp;
    }
}
