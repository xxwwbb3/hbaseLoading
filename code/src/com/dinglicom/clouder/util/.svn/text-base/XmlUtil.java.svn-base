/**
 * 
 */
package com.dinglicom.clouder.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * @author panzhen.jack@gmail.com
 * 
 */
public class XmlUtil {
	private final static Logger LOG = Logger.getLogger(XmlUtil.class);

	/**
	 * @param xml
	 * @param node
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static List<Element> getElements(String xml, String node) {
		Document doc = getDocument(xml);
		Element ele = (Element) doc.selectSingleNode(node);
		return ele.elements();
	}

	public static Document getDocument(Resource rs) {
		Document doc = null;
		try {
			doc = getDocument(rs.getInputStream());
		} catch (FileNotFoundException e) {
			LOG.error(e);
		} catch (IOException e) {
			LOG.error(e);
		}
		return doc;
	}

	/**
	 * @param xml
	 * @return
	 */
	public static Document getDocument(String xml) {
		Document doc = null;
		try {
			doc = getDocument(new FileInputStream(xml));
		} catch (FileNotFoundException e) {
			LOG.error(e);
		}
		return doc;
	}

	/**
	 * @param in
	 * @return
	 */
	public static Document getDocument(InputStream in) {
		Document doc = null;
		SAXReader reader = new SAXReader();
		try {
			doc = reader.read(in);
			return doc;
		} catch (Exception e) {
			throw new RuntimeException("Decode config file read fail.", e);
		} finally {
			try {
				in.close();
			} catch (IOException e) {
				LOG.error(e);
			}
		}
	}
}
