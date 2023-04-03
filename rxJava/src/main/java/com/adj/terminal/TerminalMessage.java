package com.adj.terminal;


/**
 * Created by dhx on 2023/3/9
 */
public class TerminalMessage {

    private final TerminalMessageType type;
    private final byte[] data;

    public TerminalMessage(TerminalMessageType type, byte[] data) {
        this.type = type;
        this.data = data;
    }

    public TerminalMessageType getType() {
        return type;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "TerminalMessage{" +
                "type=" + type +
                ", dataLength=" + data.length +
                '}';
    }
}
