package com.marcosflobo.micronaut.etcd.demo;

import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.kv.GetResponse;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Controller("/v1/kv")
public class MyController {

  @Get(value = "{key}")
  public HttpResponse<String> get(String key) {
    // THIS WORKS USING DIRECTLY THE JETCD AND I DOES NOT WORK USING THROUGHT THE etcd-1.0.0.BUILD-SNAPSHOT-all.jar
    Client client = Client.builder().endpoints("http://localhost:2379").build();
    KV kvClient = client.getKVClient();
    ByteSequence keyb = ByteSequence.from(key.getBytes());

    CompletableFuture<GetResponse> getFuture = kvClient.get(keyb);

    try {
      GetResponse response = getFuture.get();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } catch (ExecutionException e) {
      e.printStackTrace();
    }
    return HttpResponse.ok();
  }
}
