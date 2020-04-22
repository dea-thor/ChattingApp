public class ResponseMessage {
    String textMessage;
    Boolean IsME;

    public ResponseMessage(String textMessage, Boolean isME) {
        this.textMessage = textMessage;
        IsME = isME;
    }

    public String getTextMessage() {
        return textMessage;
    }

    public void setTextMessage(String textMessage) {
        this.textMessage = textMessage;
    }

    public Boolean getME() {
        return IsME;
    }

    public void setME(Boolean ME) {
        IsME = ME;
    }
}
