package service;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//@Service(value = "petStore")
public class PetStoreService {

    public List<String> getUsernameList(){
        List<String> result = new ArrayList<String>();

        result.add("Tom");
        result.add("Jerry");
        return result;
    }
}
