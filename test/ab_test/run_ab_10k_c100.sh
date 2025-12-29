#!/bin/bash

HOST="localhost"
PORTS=(21080 21081 21082 21083 21084 21085)

TOTAL=10000
CONCURRENCY=100

RESULT_FILE="result_10k_c100.csv"

echo "port,method,rps,avg_ms,p95_ms,failed" > $RESULT_FILE

run_ab () {
  local url=$1
  local method=$2
  local port=$3
  local extra=$4

  OUTPUT=$(ab -n $TOTAL -c $CONCURRENCY $extra "$url" 2>/dev/null)

  RPS=$(echo "$OUTPUT" | grep "Requests per second" | awk '{print $4}')
  AVG=$(echo "$OUTPUT" | grep "Time per request" | head -n 1 | awk '{print $4}')
  FAILED=$(echo "$OUTPUT" | grep "Failed requests" | awk '{print $3}')
  P95=$(echo "$OUTPUT" | grep " 95%" | awk '{print $2}')

  echo "$port,$method,$RPS,$AVG,$P95,$FAILED" >> $RESULT_FILE
}

for port in "${PORTS[@]}"
do
  echo "===== Testing port $port ====="

  # GET /query
  run_ab \
    "http://$HOST:$port/query?itemId=1" \
    "GET" \
    "$port"

  # POST /create
  run_ab \
    "http://$HOST:$port/create?itemId=2&buyerId=50012&quality=12345" \
    "POST" \
    "$port" 

done

echo
echo "======================================="
echo "压测完成，结果汇总如下："
column -s, -t result_10k_c100.csv
echo "======================================="
