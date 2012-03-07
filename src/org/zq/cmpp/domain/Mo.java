package org.zq.cmpp.domain;



public class Mo extends DomainObject {
	// --------------------------------------constant
	
	// -------------------------------------properties
	
	private Long id;
	private int expindex;
	private int moindex;
	private String sequence;
	private String servicecode;
	private String msisdn;
	private String content;
	private String accessno;
	private int processno;

	// -------------------------------------------constructor

	public Mo() {
		super();
	}

	// ----------------------------------------public method

	// ----------------------------------------override method
	
	@Override
	public Key getKey() {
		return new MoKey();
	}
	
	// -------------------------------------------inner class
	
	public class MoKey implements Key {
		private Long key = id;
		
		@Override
		public int hashCode() {
			return key.hashCode();
		}
	}

	// -------------------------------------------setter and getter

	public int getExpindex() {
		return expindex;
	}

	public void setExpindex(int expindex) {
		this.expindex = expindex;
	}

	public int getMoindex() {
		return moindex;
	}

	public void setMoindex(int moindex) {
		this.moindex = moindex;
	}

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getServicecode() {
		return servicecode;
	}

	public void setServicecode(String servicecode) {
		this.servicecode = servicecode;
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

	public String getAccessno() {
		return accessno;
	}

	public void setAccessno(String accessno) {
		this.accessno = accessno;
	}

	public int getProcessno() {
		return processno;
	}

	public void setProcessno(int processno) {
		this.processno = processno;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
