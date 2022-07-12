package henu.soft.example.netty.example_http.server;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import henu.soft.example.netty.example_http.utils.ByteBufToBytes;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaderValues;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpUtil;



public class HttpServerHandlerOfClient extends ChannelInboundHandlerAdapter {

    /**
     * 工具类
     */
    private ByteBufToBytes reader;


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = (HttpRequest) msg;
            System.out.println("messageType:"+ request.headers().get("messageType"));
            System.out.println("businessType:"+ request.headers().get("businessType"));
            if (HttpUtil.isContentLengthSet(request)) {
                reader = new ByteBufToBytes(
                        (int) HttpUtil.getContentLength(request));
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent httpContent = (HttpContent) msg;
            ByteBuf content = httpContent.content();
            reader.reading(content);
            content.release();
            if (reader.isEnd()) {
                String resultStr = new String(reader.readFull());
                System.out.println("Client said:" + resultStr);
                FullHttpResponse response = new DefaultFullHttpResponse(
                        HTTP_1_1, OK, Unpooled.wrappedBuffer("I am ok"
                        .getBytes()));
                response.headers().set(CONTENT_TYPE, "text/plain");
                response.headers().set(CONTENT_LENGTH,
                        response.content().readableBytes());
                response.headers().set(CONNECTION, HttpHeaderValues.KEEP_ALIVE);
                ctx.write(response);
                ctx.flush();
            }
        }
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
