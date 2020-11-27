# micronaut-etcd-demo
Demo micronaut app to validate the [Micronaut integration micronaut-etcd](https://github.com/marcosflobo/micronaut-etcd).

## Steps to test the micronaut-etcd integration
Please follow the steps below to test the integration

### Run local docker etcd server
```shell script
docker run -it --rm -p 2379:2379 -p 4001:4001 --name myetcd quay.io/coreos/etcd:v3.4.9 /usr/local/bin/etcd -advertise-client-urls http://0.0.0.0:2379 -listen-client-urls http://0.0.0.0:2379
```

### Download and use official etcdctl
#### Download
```shell script

# choose either URL
GOOGLE_URL=https://storage.googleapis.com/etcd
GITHUB_URL=https://github.com/etcd-io/etcd/releases/download
DOWNLOAD_URL=${GOOGLE_URL}

rm -f /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz
rm -rf /tmp/etcd-download-test && mkdir -p /tmp/etcd-download-test

curl -L ${DOWNLOAD_URL}/${ETCD_VER}/etcd-${ETCD_VER}-linux-amd64.tar.gz -o /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz
tar xzvf /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz -C /tmp/etcd-download-test --strip-components=1
rm -f /tmp/etcd-${ETCD_VER}-linux-amd64.tar.gz

/tmp/etcd-download-test/etcd --version
/tmp/etcd-download-test/etcdctl version
```
#### Run the etcdctl
So you can test the server works well
```shell script
/tmp/etcd-download-test/etcdctl get foo --endpoints=localhost:2379
```
You should have empty response, since `foo` key has never been put before ;)

### Generate micronaut-etcd jar locally
Let's create the JAR for [micronaut-etcd](https://github.com/marcosflobo/micronaut-etcd) to be stored
in the local maven
```shell script
git clone https://github.com/marcosflobo/micronaut-etcd.git
cd micronaut-etcd/
./gradlew clean pTML
```

### Test the integration
Clone the current repo [micronaut-etcd-demo](https://github.com/marcosflobo/micronaut-etcd-demo) and run:
```shell script
./gradlew run
```
So you will have this demo listening on `http://localhost:8080` and it's ready to receive requests.
#### Put values
##### String values
```shell script
 curl -X PUT http://localhost:8080/v1/kv/ --header "Content-Type: application/json" -d '{"key":"foo","value":"bar"}'
```
#### Get values
```shell script
# GET value for "foo" key
curl -X GET http://localhost:8080/v1/kv/foo --header "Content-Type: application/json"
```