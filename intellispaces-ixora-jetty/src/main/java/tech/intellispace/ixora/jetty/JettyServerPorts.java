package tech.intellispace.ixora.jetty;

import tech.intellispaces.ixora.http.HttpPortExchangeChannel;

public interface JettyServerPorts {

  static MovableJettyServerPortHandle get(
      int portNumber,
      Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return new JettyServerPortHandleSimpleImpl(portNumber, exchangeChannel);
  }
}
