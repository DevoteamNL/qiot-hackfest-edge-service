#!/bin/bash
podman run -d -p 8080:8080 --network=qiot --name=air-quality-edge bentaljaard/air-quality-edge:aarch64-latest
cp *.service /etc/systemd/system
systemctl enable air-quality-edge.service
systemctl start air-quality-edge
#systemctl status air-quality-edge
