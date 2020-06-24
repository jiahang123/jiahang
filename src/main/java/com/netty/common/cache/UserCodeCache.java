package com.netty.common.cache;

import com.netty.common.cache.xml.UserCode;
import com.netty.common.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.dom4j.Element;
import org.springframework.cache.annotation.Cacheable;

import java.io.InputStream;
import java.io.ObjectStreamException;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;


/**
 * @version: 1.0.0
 *  @Description: 
 * @author: H
 * @date: 2019-10-29 10:43:46
 */
@Slf4j
@Cacheable
public class UserCodeCache<T> {

    private static volatile UserCodeCache instance = new UserCodeCache();
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private Map<String, T> codeMap = null;
    private Class<T> clazz;

    private UserCodeCache() {
        clazz = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
    }
    public static UserCodeCache getInstance() {
        return instance;
    }
    private Object readResolve() throws ObjectStreamException {
        return getInstance();

    }

    protected  String xmlName() {
        getInstance();
       return  clazz.getClass().getSimpleName();
    }

    private static final String LOGBACK  = "code/"+getInstance().xmlName()+".xml";

    // 初始化
    public void reload() {
        try {
            this.readWriteLock.writeLock().lock();
            if(!StringUtils.isNullOrEmpty(codeMap)){
                this.codeMap.clear();
            }
            this.codeMap = this.readCode();
        } catch (Exception var5) {
           var5.getStackTrace();
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }
    
   /**
    * @Description: 读取 dom 解析
    * @return 返回map
    * @author: H
    */
   public Map<String, T> readCode() {
    	InputStream is= UserCodeCache.class.getResourceAsStream("/"+LOGBACK);
        XmlUtils xmlTool = new XmlUtils(is);
        Element elmRoot = xmlTool.getRootElement();
        Map<String, T> itemMap = new HashMap<>(16);

        // type 这一层
        Iterator<?> iteratorType = elmRoot.elementIterator("type");

        while(iteratorType.hasNext()) {
            Element elementType = (Element)iteratorType.next();
            String idType = elementType.attributeValue("id");
            String codeType = elementType.attributeValue("code");
            Iterator<?> iteratorItem = elementType.elementIterator("item");

            while(iteratorItem.hasNext()) {
                Element elementItem = (Element)iteratorItem.next();
                String ident = elementItem.attributeValue("ident");
                String pre = elementItem.attributeValue("pre");
                String codeItem = elementItem.attributeValue("code");
                String msgZh = elementItem.attributeValue("msgZh");
                String msgEn = elementItem.attributeValue("msgEn");
                UserCode serviceUserCode = new UserCode();
                serviceUserCode.setCode(codeType + pre + codeItem);
                serviceUserCode.setMsgZh(msgZh);
                serviceUserCode.setMsgEn(msgEn);
                itemMap.put(idType + "." + ident, (T) codeMap);
            }
        }

        return itemMap;
    }
}
