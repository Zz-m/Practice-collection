package com.adj.terminal;

/**
 * Created by dhx on 2023/3/9
 */
public enum TerminalMessageType {
    //更新数据
    UPDATE_CABIN_STATUS(1),

    //同步控件
    SYNC_WIDGET_LIGHT_DENSITY(51),
    SYNC_WIDGET_LIGHT_TEMPERATURE(52),
    SYNC_FAN_SPEED(53),
    ;

    public final int id;

    TerminalMessageType(int id) {
        this.id = id;
    }

    public static TerminalMessageType getById(int id) {
        for (TerminalMessageType type : TerminalMessageType.values()) {
            if (type.id == id) return type;
        }
        throw new RuntimeException("Invalid id: " + id);
    }
}
