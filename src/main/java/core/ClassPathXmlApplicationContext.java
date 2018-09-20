package core;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import service.PetStoreService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ClassPathXmlApplicationContext {

    private final Map<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>(256);
    private final Map<String, Object> singletonObjects = new ConcurrentHashMap<>(256);

    public ClassPathXmlApplicationContext(String configLoaction) {

        //定位配置文件
        URL path = Thread.currentThread().getContextClassLoader().getResource(configLoaction);

        try {
            File f = new File(path.toURI());

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = factory.newDocumentBuilder();
            Document doc = docBuilder.parse(f);
            Element root = doc.getDocumentElement();
            NodeList nl = root.getChildNodes();
            System.out.println(nl.getLength());
            for (int i = 0;i < nl.getLength();i++){
                Node node = nl.item(i);
                if (node instanceof Element){

                    BeanDefinition bd = new BeanDefinition();

                    Element ele = (Element) node;
                    if("bean".equals(node.getNodeName())){

                        //解析bean
                        String id = ele.getAttribute("id");
                        bd.setId(id);

                        String nameAttr = ele.getAttribute("name");
                        bd.setName(nameAttr);

                        String className = null;
                        if (ele.hasAttribute("class")) {
                            className = ele.getAttribute("class").trim();
                            bd.setBeanClass(className);
                        }
                        beanDefinitionMap.put(nameAttr,bd);
                    }
                }

            }

        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        //初始化bea
        for (String key : beanDefinitionMap.keySet()){
            BeanDefinition bd = beanDefinitionMap.get(key);
            System.out.println(bd.getBeanClass());
            try {
                Class clazz = Class.forName(bd.getBeanClass());
                Constructor constructor = clazz.getConstructor();
                Object obj = (PetStoreService) constructor.newInstance();
                singletonObjects.put(bd.getId(),obj);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }

    }

    public <T> T getBean(String name,Class<T> requiredType){
        return (T) singletonObjects.get(name);
    }
}
