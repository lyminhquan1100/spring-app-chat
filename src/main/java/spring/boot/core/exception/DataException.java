package spring.boot.core.exception;

import spring.boot.core.msg.Message;

public class DataException extends BaseException {
  private final static int ERROR_CODE = 600;

  protected DataException(int code,String message) {
    super(ERROR_CODE+code,message);
  }

  public static class InvalidId extends DataException{
    public InvalidId(Long id){
      super(1, Message.getMessage("DataException.invalidId",new Object[]{id}));
    }
  }

  public static class NotFoundEntityById extends DataException{
    public NotFoundEntityById(Long id,String entity){
      super(2,Message.getMessage("DataException.NotfoundEntityById",new Object[]{String.valueOf(id),entity}));
    }
  }

  public static class NotExistData extends DataException{
    public NotExistData(){
      super(3,Message.getMessage("DataException.NotExistData"));
    }
    public NotExistData(String message){
      super(3,Message.getMessage("DataException.NotExistData",new Object[]{message}));
    }
  }

  public static class NullOrEmpty extends DataException{
    public NullOrEmpty(String nameField){
      super(4,Message.getMessage("DataException.NullOrEmpty",new Object[]{nameField}));
    }
  }

}
