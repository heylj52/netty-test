package object;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectServerHandler extends SimpleChannelInboundHandler<User> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
        User user = msg;
        System.out.println(user);
        user.setAge(30);


        ChannelFuture cf = ctx.writeAndFlush(user);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()) {
                    System.out.println("서버에서 전송 성공");

                } else {
                    System.out.println("서버에서 전송  실패");
                }
            }
        });
    }

    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
