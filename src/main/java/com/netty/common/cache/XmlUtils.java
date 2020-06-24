package com.netty.common.cache;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * @Description:    ResultCode.xml xml 解析工具
 * @author H
 */
public class XmlUtils {
    private Document doc;
   

    public XmlUtils(InputStream in) {
        this.doc = null;
        Reader reader =  null;
        try {
        	reader = new InputStreamReader(in, "utf-8");
            SAXReader sax = new SAXReader();
            this.doc = sax.read(reader);
        } catch (Exception var11) {
            var11.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException var10) {
                var10.printStackTrace();
            }

        }
    }

    public Element getRootElement() {
        return this.doc.getRootElement();
    }

    public Document getDoc() {
        return this.doc;
    }
}
