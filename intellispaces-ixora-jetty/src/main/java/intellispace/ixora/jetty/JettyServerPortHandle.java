package intellispace.ixora.jetty;

import intellispaces.jaquarius.annotation.Mapper;
import intellispaces.jaquarius.annotation.Mover;
import intellispaces.jaquarius.annotation.ObjectHandle;
import intellispaces.jaquarius.exception.TraverseException;
import intellispaces.jaquarius.object.MovableObjectHandle;
import intellispaces.jaquarius.space.channel.ChannelFunctions;
import intellispaces.ixora.http.HttpPortExchangeChannel;
import intellispaces.ixora.http.InboundHttpPortDomain;
import intellispaces.ixora.http.MovableInboundHttpPort;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletHandler;
import org.eclipse.jetty.servlet.ServletHolder;

@ObjectHandle(JettyServerPortDomain.class)
public abstract class JettyServerPortHandle implements MovableJettyServerPort {
  private final int portNumber;
  private final Class<? extends HttpPortExchangeChannel> exchangeChannel;
  private final Server server;
  private final JettyServlet servlet;

  public JettyServerPortHandle(
      int portNumber,
      Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    this.portNumber = portNumber;
    this.exchangeChannel = exchangeChannel;

    this.server = new Server();

    var connector = new ServerConnector(server);
    connector.setPort(portNumber);
    server.setConnectors(new Connector[] { connector });

    ServletHandler servletHandler = new ServletHandler();
    server.setHandler(servletHandler);

    servlet = new JettyServlet();
    ServletHolder servletHolder = new ServletHolder(servlet);
    servletHandler.addServletWithMapping(servletHolder, "/");
  }

  @Mover
  @Override
  public MovableInboundHttpPort open() {
    try {
      Class<? extends InboundHttpPortDomain> actualPortDomain = getActualPortDomain(exchangeChannel);
      MovableObjectHandle<?> actualPort = mapTo(actualPortDomain);
      if (actualPort == null) {
        throw TraverseException.withMessage("Could not define actual port");
      }
      servlet.init(actualPort, exchangeChannel);

      server.start();
    } catch (Exception e) {
      throw TraverseException.withCauseAndMessage(e, "Could not open HTTP server");
    }
    return this.asInboundHttpPort();
  }

  @Mover
  @Override
  public MovableInboundHttpPort close() {
    stopServer();
    return this.asInboundHttpPort();
  }

  @Override
  public void release() {
    stopServer();
  }

  private void stopServer() {
    try {
      server.stop();
    } catch (Exception e) {
      throw TraverseException.withCauseAndMessage(e, "Could not close HTTP server");
    }
  }

  @Mapper
  @Override
  public Integer portNumber() {
    return portNumber;
  }

  @Mapper
  @Override
  public int portNumberPrimitive() {
    return portNumber;
  }

  @SuppressWarnings("unchecked")
  private Class<? extends InboundHttpPortDomain> getActualPortDomain(
      Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return (Class<? extends InboundHttpPortDomain>) ChannelFunctions.getChannelSourceDomainClass(exchangeChannel);
  }
}
