package org.zq.cmpp.domain;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public abstract class DomainObject {

	private Map<String, Object> userDataMap = new ConcurrentHashMap<String, Object>();

	public final Object getData(final String name) {
		return userDataMap.get(name);
	}

	public final void setData(final String name, final Object userData) {
		userDataMap.put(name, userData);
	}
	
	void copyFrom(Object base) {
        if (base instanceof DomainObject) {
            this.userDataMap.putAll(((DomainObject)base).userDataMap);
        }
    }
	
	public abstract Key getKey();
	
	public static interface Key {	
	}
	
	// ----------------------------------------override method
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof DomainObject))
			return false;

		if (this == obj)
			return true;

		DomainObject ref = (DomainObject) obj;

		return ref.getKey().equals(this.getKey()) ? true : false;
	}

	@Override
	public int hashCode() {
		return this.getKey().hashCode();
	}
}
