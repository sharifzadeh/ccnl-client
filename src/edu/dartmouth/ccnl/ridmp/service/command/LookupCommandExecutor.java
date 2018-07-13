package edu.dartmouth.ccnl.ridmp.service.command;

import java.rmi.RemoteException;

/**
 * @author: Amir H. Sharifzadeh
 * @RIDMP Service
 */
public interface LookupCommandExecutor {
    boolean canExecute(LookupCommand command, 
                       String name) throws RemoteException;

    Object executeCommand(CommandInterface registeryCommand, 
                          LookupCommand command) throws RemoteException;
}
