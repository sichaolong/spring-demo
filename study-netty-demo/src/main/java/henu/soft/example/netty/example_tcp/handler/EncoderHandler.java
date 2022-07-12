package henu.soft.example.netty.example_tcp.handler;

import henu.soft.example.netty.example_tcp.protocol.MyTcpProtocol;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 编码器是服务器按照协议格式返回数据给客户端时候调用的，继承MessageToByteEncoder代码：
 */

public class EncoderHandler extends MessageToByteEncoder {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (msg instanceof MyTcpProtocol){
            MyTcpProtocol protocol = (MyTcpProtocol) msg;
            out.writeByte(protocol.getHeader());
            out.writeInt(protocol.getLen());
            out.writeBytes(protocol.getData());
            out.writeByte(protocol.getTail());
            logger.debug("数据编码成功："+out);
        }else {
            logger.info("不支持的数据协议："+msg.getClass()+"\t期待的数据协议类是："+MyTcpProtocol.class);
        }
    }
}

