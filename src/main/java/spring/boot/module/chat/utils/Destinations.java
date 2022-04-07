package spring.boot.module.chat.utils;

import java.util.HashMap;
import java.util.Map;

public class Destinations {
    public static String publicPack() {
        return "/broker/public";
    }

    public static String userPack(Long id){
        return "/broker/user/" + id;
    }

    public static String devicePack(Long id){
        return "/broker/device/" + id;
    }

    public static Map<String,Object> sendData(String type,Object data){
        return new HashMap<String,Object>(){{
            put("type",type);
            put("data",data);
        }};
    }

}
