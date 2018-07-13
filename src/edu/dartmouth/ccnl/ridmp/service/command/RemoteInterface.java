package edu.dartmouth.ccnl.ridmp.service.command;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RemoteInterface extends Remote{

    public String capitalize(String str) throws RemoteException;
}