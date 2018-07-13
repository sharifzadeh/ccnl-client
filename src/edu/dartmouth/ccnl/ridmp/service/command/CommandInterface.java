package edu.dartmouth.ccnl.ridmp.service.command;

import edu.dartmouth.ccnl.ridmp.ShapeConfiguration;
import edu.dartmouth.ccnl.ridmp.dto.FeatureTargetAwardTO;
import edu.dartmouth.ccnl.ridmp.dto.PersonTO;
import edu.dartmouth.ccnl.ridmp.dto.ScheduleTO;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author: Amir H. Sharifzadeh
 */
public interface CommandInterface extends Remote {
    public void saveShape(ShapeConfiguration shapeTO) throws RemoteException;

    public void saveSchedule(ScheduleTO scheduleTO) throws RemoteException;

    public ShapeConfiguration loadShape() throws RemoteException;

    public ScheduleTO loadSchedule() throws RemoteException;

    public PersonTO authorized(PersonTO personTO) throws RemoteException;

    public void updatePerson(PersonTO personTO) throws RemoteException;

    public void saveTargets(List<FeatureTargetAwardTO> targets) throws RemoteException;

    public int saveTarget(FeatureTargetAwardTO target) throws RemoteException;

    public void sentEmail(PersonTO personTO, int status) throws RemoteException;

    public Integer lastAttemptedPerson(Integer perId) throws RemoteException;

    public void exportAwardCSV(PersonTO personTO) throws RemoteException;
}