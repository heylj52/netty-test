package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CommonHandler extends ChannelInboundHandlerAdapter {

  @Override
  public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
    log.info("CommonHandler.channelRegistered");

  }

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    log.info("CommonHandler.channelActive {}", ctx.channel().localAddress());
    String sendMessage = "Hello! "+ctx.channel().localAddress();

    ctx.writeAndFlush(Unpooled.buffer().writeBytes(sendMessage.getBytes()));
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    log.info("CommonHandler.channelRead");
    log.info("받은 메세지: " + ((ByteBuf)msg).toString(Charset.defaultCharset()));
    ctx.pipeline().remove(this);
  }
}
