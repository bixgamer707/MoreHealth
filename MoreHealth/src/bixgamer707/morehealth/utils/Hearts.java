package bixgamer707.morehealth.utils;

public class Hearts {
	
	private String uuid;
    private int hearts;
    
    public Hearts(String uuid, int hearts) {
        this.uuid = uuid;
        this.hearts = hearts;
    }
    public String getUuid() {
        return uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public int getHearts() {
        return hearts;
    }
    public void setHearts(int hearts) {
        this.hearts = hearts;
    }
}
