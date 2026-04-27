FROM ubuntu:latest
LABEL authors="Marcos"

ENTRYPOINT ["top", "-b"]