package com.sfc.authorizationserver.util;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getAuthenticatorResponse", namespace = "http://webServiceAutenticacion.action.superfinanciera.nexura.sc.com.co/")
public class GetAuthenticatorResponse {
	@XmlElement(name = "return")
	private ReturnResponse returnResponse;

	public ReturnResponse getReturnResponse() {
		return returnResponse;
	}

	public void setReturnResponse(ReturnResponse returnResponse) {
		this.returnResponse = returnResponse;
	}
}