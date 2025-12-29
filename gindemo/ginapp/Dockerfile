FROM golang:1.24.11-alpine3.22 AS builder

# Use Chinese module proxy and sumdb so go mod download works in China
ENV GOPROXY=https://goproxy.cn,direct \
    GOSUMDB=sum.golang.google.cn \
    GO111MODULE=on

WORKDIR /src

# install git & ca-certificates (needed to fetch modules & verify TLS)
RUN apk add --no-cache git ca-certificates && update-ca-certificates || true

# Cache dependencies if go.mod/go.sum haven't changed
COPY go.mod go.sum ./
RUN go mod download

# Copy the rest of the sources and build a statically linked, trimmed binary
COPY . .
ENV CGO_ENABLED=0 GOOS=linux GOARCH=amd64
RUN go build -trimpath -ldflags="-s -w" -v -o /usr/local/bin/app ./...

# Final runtime image: small, non-root
FROM alpine:3.22

# add CA certs (useful if your app performs HTTPS calls)
RUN apk add --no-cache ca-certificates

# create an unprivileged user to run the process
RUN addgroup -S app && adduser -S -G app app

# copy the built binary from the builder stage
COPY --from=builder /usr/local/bin/app /usr/local/bin/app

# application data (your perf.db). Place it under /var/lib/app and make sure permissions are correct.
RUN mkdir -p /var/lib/app && chown -R app:app /var/lib/app
COPY --chown=app:app perf.db /home/app/perf.db

USER app
WORKDIR /var/lib/app

ENV PATH=/usr/local/bin:$PATH
ENV PATH=/home/app/:$PATH

# keep the same default command as your original Dockerfile
CMD ["app"]