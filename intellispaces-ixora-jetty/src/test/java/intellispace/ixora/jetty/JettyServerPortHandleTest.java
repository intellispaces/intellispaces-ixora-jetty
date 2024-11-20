package intellispace.ixora.jetty;

import intellispaces.ixora.http.HttpPortExchangeChannel;
import intellispaces.ixora.http.MovableInboundHttpPort;
import intellispaces.ixora.http.test.AbstractInboundHttpPortTest;
import intellispaces.ixora.http.test.TestPortExchangeGuideImpl;
import intellispaces.jaquarius.annotation.Preprocessing;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Tests for {@link JettyServerPortHandle} class.
 */
@Preprocessing(TestPortExchangeGuideImpl.class)
public class JettyServerPortHandleTest extends AbstractInboundHttpPortTest {

  @BeforeEach
  public void init() {
    super.init();
  }

  @AfterEach
  public void deinit() {
    super.deinit();
  }

  @Override
  public MovableInboundHttpPort getOperativePort(
      int portNumber, Class<? extends HttpPortExchangeChannel> exchangeChannel
  ) {
    return JettyServerPorts.get(portNumber, exchangeChannel).asInboundHttpPort();
  }

  @Test
  public void testHello() throws Exception {
    super.testHello();
  }
}
