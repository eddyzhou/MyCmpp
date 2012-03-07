package org.zq.cmpp.db.dao;


import java.util.List;

import org.zq.cmpp.db.DbException;
import org.zq.cmpp.domain.Mo;
import org.zq.cmpp.domain.Mt;
import org.zq.cmpp.domain.Mt.Status;


public interface CmppDao {
	public void insertMo(Mo mo) throws DbException;
	
	public void deleteMt(Mt mt) throws DbException;
	
	public void updateMtStatusTo(Status status) throws DbException;
	
	public void move2HistoryTable(Mt mt) throws DbException;
	
	public List<Mt> retrieveMts() throws DbException;
}
