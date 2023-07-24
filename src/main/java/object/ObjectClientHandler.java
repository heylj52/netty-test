package object;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ObjectClientHandler extends SimpleChannelInboundHandler<User>{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        User user = new User("hong", 25);
        ChannelFuture cf= ctx.writeAndFlush(user);
        cf.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if(future.isSuccess()) {
                    System.out.println("클라이언트에서 전송성공");
                }
                else {
                    System.out.println("클라이언트에서  전송실패");
                }
            }
        });
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, User msg) throws Exception {
        User user = msg;
        System.out.println(user);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
