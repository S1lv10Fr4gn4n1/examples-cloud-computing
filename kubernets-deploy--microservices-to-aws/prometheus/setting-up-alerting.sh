#!/bin/bash

# list secrets
kubectl get secret -n monitoring

# first delete the old secret
kubectl delete secret -n monitoring alertmanager-monitoring-kube-prometheus-alertmanager

# create new secret with alertmanager.yaml configuration (use the alertmanager.yaml name)
kubectl create  secret generic --from-file=alertmanager.yaml -n monitoring alertmanager-monitoring-kube-prometheus-alertmanager