package henu.soft.example.netty.example_tcp.protocol;


/**
 * 设计基于TCP的数据传输协议 :
 *
 * 这里使用16进制表示协议的开始位和结束位，
 * 其中0x58代表开始，0x63代表结束，
 * 均用一个字节来进行表示。
 */
public class MyTcpProtocol {
    private byte header=0x58;
    private int len;
    private byte [] data;
    private byte tail=0x63;

    public byte getTail() {
        return tail;
    }

    public void setTail(byte tail) {
        this.tail = tail;
    }

    public MyTcpProtocol(int len, byte[] data) {
        this.len = len;
        this.data = data;
    }

    public MyTcpProtocol() {
    }

    public byte getHeader() {
        return header;
    }

    public void setHeader(byte header) {
        this.header = header;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
