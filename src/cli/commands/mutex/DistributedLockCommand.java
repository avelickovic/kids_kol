package cli.commands.mutex;

import app.AppConfig;
import cli.commands.CLICommand;
import mutex.DistributedMutex;

public class DistributedLockCommand implements CLICommand {

    private DistributedMutex mutex;

    public DistributedLockCommand(DistributedMutex mutex) {
        this.mutex = mutex;
    }

    @Override
    public String commandName() {
        return "distributed_lock";
    }

    @Override
    public void execute(String args) {
        if(mutex!=null){
            AppConfig.timestampedStandardPrint("Trying to get lock...");
            mutex.lock();
            AppConfig.timestampedStandardPrint("Got lock!");
        }
            else {
                AppConfig.timestampedErrorPrint("Executing lock without a mutex_type set in configuration file. Aborting.");
            }
    }
}
