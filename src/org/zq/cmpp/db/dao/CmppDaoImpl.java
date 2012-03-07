package org.zq.cmpp.db.dao;

import java.lang.ref.WeakReference;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.WeakHashMap;

import javax.sql.DataSource;

import org.zq.cmpp.db.DataSourceFactory;
import org.zq.cmpp.db.DbException;
import org.zq.cmpp.db.ResultSetProcessor;
import org.zq.cmpp.db.SQLAction;
import org.zq.cmpp.db.SQLManager;
import org.zq.cmpp.domain.Mo;
import org.zq.cmpp.domain.Mt;
import org.zq.cmpp.domain.Mt.Status;

public class CmppDaoImpl extends SQLAction implements CmppDao {

	private SQLManager sqlManager = SQLManager.getManager("cmpp");

	private static WeakHashMap<String, WeakReference<CmppDaoImpl>> cacle = new WeakHashMap<String, WeakReference<CmppDaoImpl>>();

	private CmppDaoImpl(DataSource ds) {
		this.setDataSource(ds);
	}

	public static CmppDaoImpl getInstance(DataSource ds) {
		WeakReference<CmppDaoImpl> ref = cacle.get(ds.toString());
		if (ref == null || ref.get() == null) {
			ref = new WeakReference<CmppDaoImpl>(new CmppDaoImpl(ds));
			cacle.put(ds.toString(), ref);
		}
		return ref.get();
	}

	public static CmppDaoImpl getInstance() {
		return getInstance(DataSourceFactory.getDataSource());
	}

	// ---------------------------------------------------override method
	@Override
	public void insertMo(Mo mo) throws DbException {
		try {
			this.update(sqlManager.getSQL("insertMo"), mo2SQLParam(mo));
		} catch (SQLException e) {
			final String message = "Insert mo failure.";
			throw new DbException(message, e);
		}
	}

	private Object[] mo2SQLParam(Mo mo) {
		if (mo == null) {
			throw new NullPointerException("No mo to convert to sql param.");
		}
		Object[] params = new Object[8];
		params[0] = mo.getExpindex();
		params[1] = mo.getMoindex();
		params[2] = mo.getSequence();
		params[3] = mo.getServicecode();
		params[4] = mo.getMsisdn();
		params[5] = mo.getContent();
		params[6] = mo.getAccessno();
		params[7] = mo.getProcessno();
		return params;
	}

	public void deleteMt(Mt mt) throws DbException {
		try {
			this.update(sqlManager.getSQL("deleteMt"), mt.getId());
		} catch (SQLException e) {
			final String message = "Insert mo failure.";
			throw new DbException(message, e);
		}
	}

	public List<Mt> retrieveMts() throws DbException {
		try {
			return this.queryObjects(MtResultSetProcessor.getInstance(),
					sqlManager.getSQL("getMts"));
		} catch (SQLException e) {
			final String message = "Get mts failure.";
			throw new DbException(message, e);
		}
	}
	
	@Override
	public void move2HistoryTable(Mt mt) throws DbException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateMtStatusTo(Status status) throws DbException {
		// TODO Auto-generated method stub
		
	}

	// --------------------------------------------------- ResultSet processor

	static class MtResultSetProcessor implements ResultSetProcessor<Mt> {
		private static MtResultSetProcessor mtProcessor = new MtResultSetProcessor();

		private MtResultSetProcessor() {
			super();
		}

		public static MtResultSetProcessor getInstance() {
			return mtProcessor;
		}

		public Mt process(ResultSet rs) throws SQLException {
			Mt mt = new Mt();
			mt.setContent(rs.getString("CONTENT"));
			mt.setId(rs.getLong("ID"));
			mt.setMsisdn(rs.getString("DESTNUM"));
			mt.setServiceode(rs.getString("SERVCODE"));
			mt.setCreatTime(rs.getDate("INSERTTIME"));
			return mt;
		}
	}

}
