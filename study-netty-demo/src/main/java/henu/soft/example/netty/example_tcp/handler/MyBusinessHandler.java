package henu.soft.example.netty.example_tcp.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import henu.soft.example.netty.example_tcp.utils.ByteUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import lombok.Data;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MyBusinessHandler extends ChannelInboundHandlerAdapter {
    private ObjectMapper objectMapper = ByteUtils.InstanceObjectMapper();

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof byte[]) {
            logger.debug("解码后的字节码：" + new String((byte[]) msg, "UTF-8"));
            try {
                Object objectContainer = objectMapper.readValue((byte[]) msg, DTObject.class);
                if (objectContainer instanceof DTObject) {
                    DTObject data = (DTObject) objectContainer;

                    if (data.getClassName() != null && data.getObject().length > 0) {
                        Object object = objectMapper.readValue(data.getObject(), Class.forName(data.getClassName()));
                        logger.info("收到实体对象：" + object);
                    }
                }
            } catch (Exception e) {
                logger.info("对象反序列化出现问题：" + e);
            }

        }
    }
}
@Data

class DTObject {
    private String className;
    private byte[] object;
}

