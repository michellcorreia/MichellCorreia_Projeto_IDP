package br.inatel.quotation.web;

public class RegistrationDTO {

	private String host;
	private int port;
	
	public RegistrationDTO() {
		
	}
	public RegistrationDTO(String host, int port) {
		super();
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}
	
	
}
