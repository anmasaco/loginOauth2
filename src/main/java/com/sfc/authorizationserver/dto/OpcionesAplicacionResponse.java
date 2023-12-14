package com.sfc.authorizationserver.dto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "getOpcionesAplicacionResponse", namespace = "http://www.superfinanciera.gov.co/autorizacion/ns")
public class OpcionesAplicacionResponse {
	@XmlElement(name = "return")
	private OpcionesAplicacion returnData;

	public OpcionesAplicacion getOpcionesAplicacion() {
		return returnData;
	}

	public void setOpcionesAplicacion(OpcionesAplicacion returnData) {
		this.returnData = returnData;
	}

}