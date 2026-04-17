package app;

import cli.CLIParser;
import servent.SimpleServentListener;
import servent.messeges.util.FifoSenderWorker;
import servent.messeges.util.MessageUtil;

import java.util.ArrayList;
import java.util.List;


/**
 * Describes the procedure for starting a single Servent
 *
 * @author bmilojkovic
 */
public class ServentMain {

    /**
     * Command line arguments are:
     * 0 - path to servent list file
     * 1 - this servent's id
     */
    public static void main(String[] args) {
        if (args.length != 2) {
            AppConfig.timestampedErrorPrint("Please provide servent list file and id of this servent.");
        }

        int serventId = -1;
        int portNumber = -1;

        String serventListFile = args[0];

        AppConfig.readConfig(serventListFile);

        try {
            serventId = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            AppConfig.timestampedErrorPrint("Second argument should be an int. Exiting...");
            System.exit(0);
        }

        if (serventId >= AppConfig.getServentCount()) {
            AppConfig.timestampedErrorPrint("Invalid servent id provided");
            System.exit(0);
        }

        AppConfig.myServentInfo = AppConfig.getInfoById(serventId);

        try {
            portNumber = AppConfig.myServentInfo.getListenerPort();

            if (portNumber < 1000 || portNumber > 2000) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            AppConfig.timestampedErrorPrint("Port number should be in range 1000-2000. Exiting...");
            System.exit(0);
        }

        MessageUtil.initializePendingMessages();

        AppConfig.timestampedStandardPrint("Starting servent " + AppConfig.myServentInfo);

        SimpleServentListener simpleListener = new SimpleServentListener();
        Thread listenerThread = new Thread(simpleListener);
        listenerThread.start();

        List<FifoSenderWorker> senderWorkers = new ArrayList<>();
        if (AppConfig.IS_FIFO) {
            for (Integer neighbor : AppConfig.myServentInfo.getNeighbors()) {
                FifoSenderWorker senderWorker = new FifoSenderWorker(neighbor);

                Thread senderThread = new Thread(senderWorker);

                senderThread.start();

                senderWorkers.add(senderWorker);
            }

        }

        CLIParser cliParser = new CLIParser(simpleListener, senderWorkers);
        Thread cliThread = new Thread(cliParser);
        cliThread.start();

    }
}