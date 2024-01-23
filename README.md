# qbittorrent-exporter
### Quarkus fork - Lower ram usage (20-50mb idle) and faster startup!
Quick comparison between GraalVM Native and JVM
In red square - JVM, blue - GraalVM using Quarkus Framework
I was also able to further lower memory usage, as per advice from Quarkus Team and GraalVM compiler, by setting an `Xmx` value, I set it to 50mb by default

Docker
**Native Image:
RSS: 15-32mb
RAM Usage: 20-76mb**

JVM
RSS: 110-420mb
RAM Usage: 120-480mb

Let me know your thoughts!

![image](https://github.com/caseyscarborough/qbittorrent-exporter/assets/1276544/5c5ef6d0-3a75-4076-b91b-91c154173d46)

Draft PR: https://github.com/caseyscarborough/qbittorrent-exporter/pull/20

***

![Gradle Build](https://github.com/caseyscarborough/qbittorrent-exporter/actions/workflows/build.yaml/badge.svg) ![Release](https://github.com/caseyscarborough/qbittorrent-exporter/actions/workflows/release.yaml/badge.svg)

<img src="https://github.com/caseyscarborough/qbittorrent-grafana-dashboard/blob/master/images/logo.png" width=100> <img src="https://github.com/caseyscarborough/qbittorrent-grafana-dashboard/blob/master/images/prometheus.png" width=100>

This app is a Prometheus exporter for the qBittorrent application. You must have version 4.1.0 of qBittorrent or higher
for this plugin to work.

This is especially useful when integrated with
the [qbittorrent-grafana-dashboard](https://github.com/caseyscarborough/qbittorrent-grafana-dashboard).

See it on [DockerHub](https://hub.docker.com/r/caseyscarborough/qbittorrent-exporter).

## Usage

### docker

```bash
docker run \
    --name=qbittorrent-exporter \
    -e QBITTORRENT_USERNAME=username \
    -e QBITTORRENT_PASSWORD=password \
    -e QBITTORRENT_BASE_URL=http://localhost:8080 \
    -p 17871:17871 \
    vincejv/qbittorrent-exporter:latest
```

### docker-compose.yaml
```yaml
version: "3"
services:
 qbt-exporter:
    image: vincejv/qbittorrent-exporter:latest
    container_name: qbt-exporter
    networks:
      int-static-br:
        ipv4_address: 172.18.0.34
    environment:
      - QBITTORRENT_USERNAME=none
      - QBITTORRENT_PASSWORD=none
      - QBITTORRENT_BASE_URL=http://qbt.docker.internal:9089
      - QBITTORRENT_LOCALE=en-US
    extra_hosts:
      - "host.docker.internal:host-gateway"
    restart: unless-stopped
    depends_on:
      - qbittorrent
    logging:
      options:
        max-size: "20m"
        max-file: "5"
        compress: "true"
```

## Parameters

|         Parameter         |                               Function                               |      Default Value      |
|:-------------------------:|:--------------------------------------------------------------------:|:-----------------------:|
|        `-p 17871`         |                         The webservice port.                         |           N/A           |
| `-e QBITTORRENT_USERNAME` |                      The qBittorrent username.                       |         `admin`         |
| `-e QBITTORRENT_PASSWORD` |                      The qBittorrent password.                       |      `adminadmin`       |
| `-e QBITTORRENT_BASE_URL` |                      The qBittorrent base URL.                       | `http://localhost:8080` |
|   `-e QBITTORRENT_HOST`   |   The qBittorrent host. Ignored when using `QBITTORRENT_BASE_URL`.   |       `localhost`       |
|   `-e QBITTORRENT_PORT`   |   The qBittorrent port. Ignored when using `QBITTORRENT_BASE_URL`.   |         `8080`          |
| `-e QBITTORRENT_PROTOCOL` | The qBittorrent protocol. Ignored when using `QBITTORRENT_BASE_URL`. |         `http`          |

## Setup

Add the target to your `scrape_configs` in your `prometheus.yml` configuration file of your Prometheus server.

```yml
scrape_configs:

  - job_name: 'qbittorrent'
    static_configs:

      # Update your IP address and port here
      - targets: [ '192.168.1.100:17871' ]
```

## Building Locally

Build the app and the docker container using the following commands:

```bash
./gradlew build
docker build .
```
