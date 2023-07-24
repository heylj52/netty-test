package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import java.nio.charset.Charset;

public class EchoClientHandler extends ChannelInboundHandlerAdapter {

  // 소켓 채널이 최초 활성화되었을 때 실행
  @Override
  public void channelActive(ChannelHandlerContext ctx) {
    String sendMessage = "Hello Netty!";
    ByteBuf messageBuffer = Unpooled.buffer();
    messageBuffer.writeBytes(sendMessage.getBytes());

    System.out.println("전송한 문자열 [" + sendMessage + "]");

    ctx.writeAndFlush(messageBuffer); // 채널에 데이터를 기록하고(write), 기록된 데이터를 서버로 전송(flush)
  }

  // 서버로부터 수신된 데이터가 있으면 호출되는 이벤트 메서드
  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) {
    String readMessage = ((ByteBuf) msg).toString(Charset.defaultCharset());
    // 서버로부터 수신된 데이터를 문자열로 추출
    System.out.println("수신한 문자열 [" + readMessage + "]");
  }

  // channelRead 메서드 수행이 완료되고 자동으로 호출됨
  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) {
    ctx.close(); // 서버와 연결된 채널, 데이터 송수신 채널 닫히고, 클라이언트 프로그램 종료됨
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
    cause.printStackTrace();
    ctx.close();
  }
}
