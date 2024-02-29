package cn.denghanxi.model;

public record AndroidDevice(String udid, String type) {

    public boolean isReady() {
        return "device".equals(type);
    }
}
