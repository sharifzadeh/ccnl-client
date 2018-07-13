package edu.dartmouth.ccnl.ridmp.service.command;

import java.rmi.RemoteException;

/**
 * @author: Amir H. Sharifzadeh
 * @RIDMP Service
 */
public class DefaultCommandExecutor implements LookupCommandExecutor {

    private static DefaultCommandExecutor instance;

    public static DefaultCommandExecutor getInstance() {
        if (instance == null) {
            instance = new DefaultCommandExecutor();
        }
        return instance;
    }

    private DefaultCommandExecutor() {
    }

    public boolean canExecute(LookupCommand command, 
                              String name) throws RemoteException {
        return false;
    }

    public Object executeCommand(CommandInterface registeryCommand, 
                                 LookupCommand command) throws RemoteException {
        return command.lookUpRegistery(registeryCommand);
    }
}
