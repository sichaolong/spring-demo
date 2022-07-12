package henu.soft.example.netty.example_websocket;

import io.netty.channel.Channel;
import io.netty.channel.ChannelId;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ChannelGroup是netty提供用于管理web于服务器建立的通道channel的，
 * 其本质是一个高度封装的set集合，在服务器广播消息时，
 * 可以直接通过它的writeAndFlush将消息发送给集合中的所有通道中去。
 *
 * 但在查找某一个客户端的通道时候比较坑爹，必须通过channelId对象去查找，
 * 而channelId不能人为创建，所有必须通过map将channelId的字符串和channel保存起来。
 *
 *
 */

public class ChannelSupervise {
    private static ChannelGroup GlobalGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 本质是 set 集合，但是需要记录ChannelId之间的映射关系
    private static ConcurrentMap<String, ChannelId> ChannelMap = new ConcurrentHashMap();

    public static void addChannel(Channel channel) {
        GlobalGroup.add(channel);
        ChannelMap.put(channel.id().asShortText(), channel.id());
    }

    public static void removeChannel(Channel channel) {
        GlobalGroup.remove(channel);
        ChannelMap.remove(channel.id().asShortText());
    }

    public static Channel findChannel(String id) {
        return GlobalGroup.find(ChannelMap.get(id));
    }

    public static void send2All(TextWebSocketFrame tws) {
        GlobalGroup.writeAndFlush(tws);
    }
}
