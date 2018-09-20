
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import service.PetStoreService;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;


public class App {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");

        PetStoreService petStore = ctx.getBean("petService", PetStoreService.class);

        List<String> petList = petStore.getUsernameList();

        System.out.println(Arrays.toString(petList.toArray()));



    }
}
