package com.sfc.authorizationserver.util;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.xml.sax.InputSource;

import com.sfc.authorizationserver.dto.OpcionesAplicacionResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringReader;
import java.io.StringWriter;

public class XmlToPojoConverter {

	public static GetAuthenticatorResponse convert(String xml) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
		Node getAuthenticatorResponseNode = doc
				.getElementsByTagNameNS("http://webServiceAutenticacion.action.superfinanciera.nexura.sc.com.co/",
						"getAuthenticatorResponse")
				.item(0);
		if (getAuthenticatorResponseNode == null) {
			throw new JAXBException("No se encontró el nodo <getAuthenticatorResponse> en el XML");
		}
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(getAuthenticatorResponseNode), new StreamResult(writer));
		String getAuthenticatorResponseXml = writer.toString();
		JAXBContext jaxbContext = JAXBContext.newInstance(GetAuthenticatorResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (GetAuthenticatorResponse) unmarshaller.unmarshal(new StringReader(getAuthenticatorResponseXml));
	}

	public static OpcionesAplicacionResponse convertRole(String xml, String url, String formatResponse)
			throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		dbFactory.setNamespaceAware(true);
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(new InputSource(new StringReader(xml)));
		Node getOpcionesAplicacionResponseNode = doc.getElementsByTagNameNS(
				"http://www.superfinanciera.gov.co/autorizacion/ns", "getOpcionesAplicacionResponse").item(0);
		if (getOpcionesAplicacionResponseNode == null) {
			throw new JAXBException("No se encontró el nodo " + formatResponse + " en el XML");
		}
		Transformer transformer = TransformerFactory.newInstance().newTransformer();
		StringWriter writer = new StringWriter();
		transformer.transform(new DOMSource(getOpcionesAplicacionResponseNode), new StreamResult(writer));
		String getOpcionesAplicacionResponseXml = writer.toString();
		JAXBContext jaxbContext = JAXBContext.newInstance(OpcionesAplicacionResponse.class);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		return (OpcionesAplicacionResponse) unmarshaller.unmarshal(new StringReader(getOpcionesAplicacionResponseXml));
	}
}
