package cli.commands.mutex;

import app.AppConfig;
import cli.commands.CLICommand;
import mutex.DistributedMutex;

public class DistributedUnlockCommand implements CLICommand {

    private DistributedMutex mutex;

    public DistributedUnlockCommand(DistributedMutex mutex) {
        this.mutex = mutex;
    }


    @Override
    public String commandName() {
        return "distributed_unlock";
    }

    @Override
    public void execute(String args) {
        if(mutex!=null){
            AppConfig.timestampedStandardPrint("Unlocking...");
            mutex.unlock();
        }
            else {
                AppConfig.timestampedErrorPrint("Executing unlock without a mutex_type set in configuration file. Aborting.");
            }
    }
}
