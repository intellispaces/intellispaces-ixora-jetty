package intellispace.ixora.jetty;

import intellispaces.ixora.http.HttpPortExchangeChannel;

public interface JettyServerPorts {

  static MovableJettyServerPort get(
      int portNumber,
      Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return new JettyServerPortHandleImpl(portNumber, exchangeChannel);
  }
}
