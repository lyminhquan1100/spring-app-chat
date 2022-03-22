package spring.boot.module.chat.utils;

import java.util.HashMap;
import java.util.Map;

public class Destinations {
    public static String publicMessages(Long chatRoomId) {
        return "/topic/" + chatRoomId + ".public.messages";
    }

    public static String privateMessages(Long chatRoomId) {
        return "/queue/" + chatRoomId + ".private.messages";
    }

    public static String userMessage(Long userId){
        return "/broker/chat/" + userId;
    }

    public static Map<String,Object> sendData(String type,Object data){
        Map<String,Object> mapResponse = new HashMap<String,Object>(){{
            put("type",type);
            put("data",data);
        }};
        return mapResponse;
    }

    public static String userMessage(String userId){
        return "/broker/user." + userId;
    }

    public static String connectedUsers(Long chatRoomId) {
        return "/topic/" + chatRoomId + ".connected.users";
    }

}
