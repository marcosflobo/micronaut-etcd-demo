package com.marcosflobo.micronaut.etcd.demo;

import static java.nio.charset.StandardCharsets.UTF_8;

import io.micronaut.etcd.config.EtcdFactoryConfig;
import io.micronaut.etcd.config.SingleEtcdFactoryConfig;
import io.micronaut.etcd.kv.KVService;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Body;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Put;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller("/v1/kv")
public class MyController {

  private String endpoint = "http://localhost:2379";

  @Get(value = "{key}")
  public HttpResponse<String> get(String key) throws ExecutionException, InterruptedException {
    EtcdFactoryConfig config = new SingleEtcdFactoryConfig();
    config.setEndpoints(endpoint);
    KVService kvService = new KVService(config);
    byte[] ret = kvService.get(key);
    String retString = "";
    if (ret != null) {
      retString = new String(ret, UTF_8);
    }
    return HttpResponse.ok(retString);
  }

  @Put
  public HttpResponse<String> put(@Body PutRequest putRequest)
      throws ExecutionException, InterruptedException {
    EtcdFactoryConfig config = new SingleEtcdFactoryConfig();
    config.setEndpoints(endpoint);
    KVService kvService = new KVService(config);
    kvService.put(putRequest.getKey(), putRequest.getValue());
    return HttpResponse.ok();
  }
}
