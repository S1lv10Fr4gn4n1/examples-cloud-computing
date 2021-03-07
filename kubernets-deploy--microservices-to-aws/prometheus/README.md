# Expose Prometheus and Graphana

### Phometheus
- Run `kubectl edit svc monitoring-kube-prometheus-prometheus -n monitoring`

- Go to the botton of the file and change type: ClusterIP
    - `LoadBalancer` in the cloud
    - `NodePort` in local development, adding `nodePort: 30088` under ports


### Graphana
- Run `kubectl edit svc monitoring-grafana -n monitoring`

- Go to the botton of the file and change type: ClusterIP
    - `LoadBalancer` in the cloud
    - `NodePort` in local development, adding `nodePort: 30089` under ports

- default `Graphana` access is `admin` -> `prom-operator`
