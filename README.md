# hystrix-bootiful-session
Example for springsession and hystrix in kubernetes.

## Abstract
This example shows how to use [springsession](http://projects.spring.io/spring-session/) and [hystrix](https://github.com/Netflix/Hystrix/wiki) in [kubernetes](http://kubernetes.io/). 
The springsession data is stored in redis. To discover all hystrix pods in kubernetes some functionalites from [fabric8](https://fabric8.io/) are used.

## Overview
![example overview](https://raw.githubusercontent.com/pbolle/hystrix-bootiful-session/master/doc/overview.png)

## Required software
To follow the step by step install and test instructions the following software is required:
- docker
- maven
- [minikube](https://github.com/kubernetes/minikube) 
- redis client (optional)
- apache benchmarking tool (optional)

## Step by step  instructions

Create a local kubernetes cluster with
```
minikube start
```
Point your local docker to minikube docker
```
eval $(minikube docker-env)
```
Check your docker configuration by runnig
```
docker images
```
The following output should be
```
REPOSITORY                                            TAG                 IMAGE ID            CREATED             VIRTUAL SIZE
gcr.io/google_containers/kubernetes-dashboard-amd64   v1.1.0              sha256:d023c        2 weeks ago         58.65 MB
gcr.io/google-containers/kube-addon-manager-amd64     v2                  sha256:a876f        10 weeks ago        231.1 MB
gcr.io/google_containers/pause-amd64                  3.0                 sha256:99e59        3 months ago        746.9 kB
```
Now build the example and create the docker image in minikube
```
mvn package docker:build 
```
To install all pods execute
```
kubectl create -f deployment
```
Open the kubernetes dashboard to see if all images are downloaded and all pods are running.
```
minikube dashboard
```
Now check whether redis is empty (my minikube is running on 192.168.99.100)
```
./redis-cli -h 192.168.99.100 keys '*'
```
The result should be
```
(empty list or set)
```
Now call the application and create a session that is stored in redis. Open the browser: http://192.168.99.100:8080/.
To see the generated session in redis type
```
./redis-cli -h 192.168.99.100 keys '*'
```
The result should look be
```
1) "spring:session:sessions:expires:622b9577-c948-455e-a3c1-32c89dfd513c"
2) "spring:session:expirations:1470131040000"
3) "spring:session:sessions:622b9577-c948-455e-a3c1-32c89dfd513c"
```
For an overview of hystrix open the  hystrix dashboard. Open the url http://192.168.99.100:8088/monitor/monitor.html?stream=http%3A%2F%2Fturbine-server%2Fturbine.stream in browser.
To create load for the hystrix dashboard you can use the apache benchmarking tool 
```
ab -n 1000 -c 4 http://192.168.99.100:8080/
```

## Further steps
Kill one of the springsession pods to insure that the session data is not lost.
Scale the springsession up and check the turbineserver log whether new instances are automatically discovered. 