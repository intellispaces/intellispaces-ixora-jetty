package intellispace.ixora.jetty;

import intellispaces.common.base.collection.ArraysFunctions;
import intellispaces.jaquarius.object.MovableObjectHandle;
import intellispaces.ixora.http.HttpMethods;
import intellispaces.ixora.http.HttpPortExchangeChannel;
import intellispaces.ixora.http.HttpRequest;
import intellispaces.ixora.http.HttpRequests;
import intellispaces.ixora.http.UnmovableHttpResponse;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

class JettyServlet extends HttpServlet {
  private MovableObjectHandle<?> logicalPort;
  private Class<? extends HttpPortExchangeChannel> exchangeChannel;

  void init(MovableObjectHandle<?> logicalPort, Class<? extends HttpPortExchangeChannel> exchangeChannel) {
    this.logicalPort = logicalPort;
    this.exchangeChannel = exchangeChannel;
  }

  @Override
  protected void doGet(
      HttpServletRequest servletRequest, HttpServletResponse servletResponse
  ) throws IOException {
    HttpRequest request = buildRequest(servletRequest);
    UnmovableHttpResponse response = logicalPort.mapOfMovingThru(exchangeChannel, request);
    populateServletResponse(servletResponse, response);
  }

  private HttpRequest buildRequest(HttpServletRequest req) {
    String url = req.getRequestURL().toString();
    String query = req.getQueryString();
    String uri = (query == null ? url : url + '?' + query);
    return HttpRequests.get(
      HttpMethods.get(req.getMethod()),
      uri
    );
  }

  private void populateServletResponse(
      HttpServletResponse servletResponse, UnmovableHttpResponse response
  ) throws IOException {
    if (response.status().isOk()) {
      servletResponse.setStatus(HttpServletResponse.SC_OK);
    } else {
      throw new RuntimeException();
    }

    byte[] body = ArraysFunctions.toByteArray(response.bodyStream().readAll().nativeList());
    servletResponse.getOutputStream().write(body);
  }
}
