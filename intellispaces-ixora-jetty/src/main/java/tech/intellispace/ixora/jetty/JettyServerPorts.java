package tech.intellispace.ixora.jetty;

import tech.intellispaces.ixora.http.HttpPortExchangeChannel;

public interface JettyServerPorts {

  static MovableJettyServerPort get(
      int portNumber,
      Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return new JettyServerPortHandleImpl(portNumber, exchangeChannel);
  }
}
