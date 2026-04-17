package cli.commands;

import app.AppConfig;
import cli.CLIParser;
import servent.SimpleServentListener;
import servent.messeges.util.FifoSenderWorker;

import java.util.List;

public class StopCommand implements CLICommand {

    private CLIParser parser;
    private SimpleServentListener listener;
    private List<FifoSenderWorker> senderWorkers;

    public StopCommand(CLIParser parser, SimpleServentListener listener,
                       List<FifoSenderWorker> senderWorkers) {
        this.parser = parser;
        this.listener = listener;
        this.senderWorkers = senderWorkers;
    }

    @Override
    public String commandName() {
        return "stop";
    }

    @Override
    public void execute(String args) {
        AppConfig.timestampedStandardPrint("Stopping...");
        parser.stop();
        listener.stop();
        for (FifoSenderWorker senderWorker : senderWorkers) {
            senderWorker.stop();
        }
    }
}
