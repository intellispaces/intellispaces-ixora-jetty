package tech.intellispace.ixora.jetty;

import tech.intellispaces.ixora.http.InboundHttpPortDomain;
import tech.intellispaces.jaquarius.annotation.Channel;
import tech.intellispaces.jaquarius.annotation.Domain;

@Domain("843b0222-dc2c-4a12-a75d-f98eef75bb1a")
public interface JettyServerPortDomain extends InboundHttpPortDomain {

  @Channel("3e687bb4-d4b2-4af8-91ac-0f7bb32ebb36")
  InboundHttpPortDomain asInboundHttpPort();
}
