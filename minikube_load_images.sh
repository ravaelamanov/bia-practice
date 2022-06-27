#!/bin/zsh

#minikube image load rabbitmq:3.8.2-management
#minikube image load target-service:0.0.1-SNAPSHOT
minikube image load monitoring-producer:0.0.1-SNAPSHOT
minikube image load monitoring-consumer:0.0.1-SNAPSHOT