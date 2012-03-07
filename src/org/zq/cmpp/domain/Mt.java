package org.zq.cmpp.domain;

import java.util.Date;


public class Mt extends DomainObject {
	private Long id;
	private String serviceode;
	private String msisdn;
	private String content;
	private Date creatTime;
	private Status status;
	
	public Mt() {
		super();
	}
	
	@Override
	public Key getKey() {
		return new MtKey();
	}
	
	public class MtKey implements Key {
		private Long key = id;
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getServiceode() {
		return serviceode;
	}

	public void setServiceode(String serviceode) {
		this.serviceode = serviceode;
	}

	public String getMsisdn() {
		return msisdn;
	}

	public void setMsisdn(String msisdn) {
		this.msisdn = msisdn;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getCreatTime() {
		return creatTime;
	}

	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}
	
	public static enum Status {
		Unprocessed(0), Processing(1), Reprocess_Needed(2), Unknown(-1);
		
		private int status;
		
		private Status(int status) {
			this.status = status;
		}

		public int getStatus() {
			return status;
		}
		
		public Status int2Status(int status) {
			for (Status s : Status.values()) {
				if (s.getStatus() == status) {
					return s;
				}
			}
			
			return Status.Unknown;
		}
	}
}
