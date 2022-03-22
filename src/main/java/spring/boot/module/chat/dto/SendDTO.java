package spring.boot.module.chat.dto;

public class SendDTO {
    private String type;

    private Object data;

    public SendDTO(String type, Object data){
        this.type = type;
        this.data = data;
    }
}
