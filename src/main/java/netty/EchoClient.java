package netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public final class EchoClient {
  public static void main(String[] args) throws Exception {
    EventLoopGroup group = new NioEventLoopGroup();
    try {
      Bootstrap b = new Bootstrap(); // 부트스트랩 객체 생성
      b.group(group)
          .channel(NioSocketChannel.class)
          // NioSocketChannel.class : 논블로킹 모드의 클라이언트 소켓 채널을 생성하는 클래스
          .handler(new ChannelInitializer<SocketChannel>() {
            // 클라이언트 소켓 채널의 이벤트 핸들러 설정
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
              ChannelPipeline p = ch.pipeline();
              p.addLast(new CommonHandler());
//              p.addLast(new EchoClientHandler());
            }
          });

      ChannelFuture f = b.connect("localhost", 8888).sync(); // EchoServer에 접속
      f.channel().closeFuture().sync();
    }
    finally {
      group.shutdownGracefully();
    }
  }
}