package com.adj.terminal;

/**
 * 同步命令编解码器
 * Created by dhx on 2023/3/9
 */
public class TerminalMessageCodec {

    public byte[] messageToBytes(TerminalMessage message) {
        byte[] result = new byte[message.getData().length + 2];
        TerminalMessageType type = message.getType();
        result[0] = (byte) (type.id >> 8 & 0xFF);
        result[1] = (byte) (type.id & 0xFF);
        System.arraycopy(message.getData(), 0, result, 2, message.getData().length);
        return result;
    }

    public TerminalMessage bytesToMessage(byte[] messageBytes) {
        int typeId = (messageBytes[0] << 8 & 0xFF00) + (messageBytes[1] & 0xFF);
        TerminalMessageType type = TerminalMessageType.getById(typeId);
        byte[] data = new byte[messageBytes.length - 2];
        System.arraycopy(messageBytes, 2, data, 0, data.length);
        return new TerminalMessage(type, data);
    }
}
