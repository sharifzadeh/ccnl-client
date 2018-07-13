package edu.dartmouth.ccnl.ridmp.service.command;

import java.rmi.RemoteException;

/**
 * @author: Amir H. Sharifzadeh
 * @RIDMP Service
 */
public interface LookupCommand {
    Object lookUpRegistery(CommandInterface registeryCommand) throws RemoteException;
}
