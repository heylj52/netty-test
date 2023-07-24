package netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {
  public static void main(String[] args) throws Exception {
    // NioEventLoopGroup 생성자 인자 수는 스레드 그룹 내 생성할 최대 스레드 수를 의미
    EventLoopGroup bossGroup = new NioEventLoopGroup(1); // 단일 스레드
    EventLoopGroup workerGroup = new NioEventLoopGroup();
    // 인자가 없으면 하드웨어 CPU 코어 수 * 2 (* 2(하이퍼쓰레딩))

    try {
      // 빌더 패턴으로 객체를 초기화함
      ServerBootstrap b = new ServerBootstrap(); // 서버 부트스트랩 객체 생성
      b.group(bossGroup, workerGroup) // 이벤트 루프 설정
          // bossGroup: 클라이언트 연결을 수락하는 부모 스레드 그룹
          // workerGroup: 연결된 클라이언트 소켓으로부터 데이터I/O 처리하는 자식 스레드 그룹
          .channel(NioServerSocketChannel.class) // 소켓 입출력 모드 설정
          // 서버 소켓(부모 스레드)의 입출력 모드(NIO 모드)
          // NioServerSocketChannel.class: 논블로킹 모드의 서버 소켓 채널을 생성하는 클래스
          .handler(new LoggingHandler(LogLevel.INFO))
          // 서버 소켓 채널의 이벤트 핸들러 설정. 연결에 대한 이벤트만 처리
          .childHandler(new ChannelInitializer<SocketChannel>() {
            // 연결된 클라이언트 소켓 채널 핸들러 설정. 데이터 송수신에 대한 이벤트 처리
            // initChannel 메서드: 클라이언트 소켓 채널이 생성될 때 자동으로 호출
            @Override
            public void initChannel(SocketChannel ch) { // 연결된 클라이언트 소켓 채널 초기화
              System.out.println("EchoServer.initChannel");
              ChannelPipeline p = ch.pipeline();
              // 연결된 클라이언트 소켓 채널에 설정된 파이프라인
              p.addLast(new CommonHandler());

//              p.addLast(new EchoServerHandler());
              // 접속된 클라이언트로부터 수신된 데이터를 처리하는 핸들러 등록
            }
          });
      ChannelFuture f = b.bind(8888).sync(); // 해당 port로 클라이언트 접속을 허용
      f.channel().closeFuture().sync();

    } finally {
      workerGroup.shutdownGracefully();
      bossGroup.shutdownGracefully();
    }
  }
}
