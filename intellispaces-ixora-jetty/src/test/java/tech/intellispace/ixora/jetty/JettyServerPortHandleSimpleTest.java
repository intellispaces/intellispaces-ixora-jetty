package tech.intellispace.ixora.jetty;

import tech.intellispaces.ixora.http.HttpPortExchangeChannel;
import tech.intellispaces.ixora.http.MovableInboundHttpPortHandle;
import tech.intellispaces.ixora.http.test.AbstractInboundHttpPortTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JettyServerPortHandleSimple} class.
 */
public class JettyServerPortHandleSimpleTest extends AbstractInboundHttpPortTest {

  @BeforeEach
  public void init() {
    super.init();
  }

  @AfterEach
  public void deinit() {
    super.deinit();
  }

  @Override
  public MovableInboundHttpPortHandle getOperativePort(
      int portNumber, Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return JettyServerPorts.get(portNumber, exchangeChannel).asInboundHttpPort();
  }

  @Test
  public void testHello() throws Exception {
    super.testHello();
  }
}
