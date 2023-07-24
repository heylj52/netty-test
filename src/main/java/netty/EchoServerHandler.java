package netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;

public class EchoServerHandler extends ChannelInboundHandlerAdapter {

  // 기본적으로 클라이언트가 데이터를 전송하면 네티가 자동으로 호출하는 이벤트 메서드
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    String readMessage = ((ByteBuf)msg).toString(Charset.defaultCharset());
    // 수신된 데이터를 가지고 있는 바이트 버퍼로부터 데이터를 읽음
    System.out.println("수신한 문자열[" + readMessage + ']'); // 수신된 메시지를 출력
    ctx.write(msg);
  }

  // channelRead 이벤트가 처리된 후 자동으로 수행되는 이벤트 메서드
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.flush(); // 채널 버퍼에 저장된 데이터를 상대방으로 전송
  }
}
