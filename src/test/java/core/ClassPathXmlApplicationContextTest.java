package core;


import service.PetStoreService;

import java.util.Arrays;

public class ClassPathXmlApplicationContextTest {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
        PetStoreService petStoreService = ctx.getBean("petService",PetStoreService.class);
        System.out.println(Arrays.toString(petStoreService.getUsernameList().toArray()));

    }
}
