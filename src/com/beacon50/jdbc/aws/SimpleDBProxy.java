package com.beacon50.jdbc.aws;

import java.util.Properties;

public class SimpleDBProxy {

	private Properties props;

	public SimpleDBProxy(final Properties props) {
		this.props = props;
	}

	public String getPassword() {
		return props.getProperty("proxyPassword", "");
	}

	public String getUsername() {
		return props.getProperty("proxyUsername", "");
	}

	public String getHost() {
		return props.getProperty("proxyHost", "");
	}

	public int getPort() {
		return Integer.parseInt(props.getProperty("proxyPort", "80"));
	}
	
	public String toString(){
		return "pssword: " + getPassword() + " username: " + getUsername() +
			" host : " + getHost() + " port: " + getPort(); 
	}
}
