package edu.dartmouth.ccnl.ridmp.service.command;

import java.rmi.RemoteException;

/**
 * @author: Amir H. Sharifzadeh
 * @RIDMP Service
 */
public interface RegisteryCommandExecutor {
    /**
     * decides whether the command executor can execute the {@link LookupCommand}or not.
     */
    boolean canExecute(String name, 
                       LookupCommand command) throws RemoteException;

    /**
     * executes the {@link LookupCommand}and returns the result.
     */
    Object executeCommand(String name, 
                          LookupCommand command) throws RemoteException;
}
