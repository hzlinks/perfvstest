#!/usr/bin/env bash
set -euo pipefail
IFS=$'\n\t'

# 可通过环境变量覆盖
JMETER_CMD=${JMETER_CMD:-jmeter}
OUTPUT_DIR=${OUTPUT_DIR:-./jmeter-reports}
PARALLEL=${PARALLEL:-0}        # 0: 串行（默认），1: 并行
MAX_JOBS=${MAX_JOBS:-2}        # 并发最大任务数（仅在 PARALLEL=1 时生效）

PORTS=(21080 21081 21082 21083 21084 21085)

mkdir -p "$OUTPUT_DIR"

# 单次运行函数
run_test() {
  local port=$1
  local jmx_file="perf_${port}_test_10k_c100.jmx"
  local ts
  ts=$(date +%Y%m%d%H%M%S)
  local report_dir="${OUTPUT_DIR}/report_${port}_${ts}"
  local result_file="${report_dir}/result_${port}.jtl"
  mkdir -p "$report_dir"

  if ! command -v "$JMETER_CMD" >/dev/null 2>&1; then
    echo "ERROR: $JMETER_CMD not found in PATH" >&2
    return 2
  fi

  if [[ ! -f "$jmx_file" ]]; then
    echo "WARNING: JMX file not found: $jmx_file, skipping port $port" >&2
    return 1
  fi

  echo "===== Testing port $port (jmx=$jmx_file) ====="
  echo "$JMETER_CMD" -n -t "$jmx_file" -l "$result_file" -e -o "$report_dir" >>"jmeter.log"
  # 将 jmeter 的 stdout/stderr 都写到 log，jmeter 的 -e -o 输出到 report_dir
  if ! "$JMETER_CMD" -n -t "$jmx_file" -l "$result_file" -e -o "$report_dir" >>"jmeter.log" 2>&1; then
    echo "ERROR: jmeter failed for port $port, see ${report_dir}/jmeter.log" >&2
    return 1
  fi

  echo "Completed port $port -> $report_dir"
  return 0
}

# 并发控制（非常简单的实现）
run_parallel() {
  local -a pids=()
  for port in "${PORTS[@]}"; do
    run_test "$port" &
    pids+=($!)
    # 限制并发数
    while (( ${#pids[@]} >= MAX_JOBS )); do
      sleep 1
      # 清理已退出的 PID
      local -a newp=()
      for pid in "${pids[@]}"; do
        if kill -0 "$pid" 2>/dev/null; then
          newp+=("$pid")
        fi
      done
      pids=("${newp[@]}")
    done
  done
  wait
}

# 主逻辑
if [[ "$PARALLEL" -ne 0 ]]; then
  echo "Running in parallel mode (MAX_JOBS=${MAX_JOBS})"
  run_parallel
else
  for port in "${PORTS[@]}"; do
    run_test "$port" || echo "Test failed or skipped for port $port"
  done
fi

echo
echo "======================================="
echo "压测完成"
echo "======================================="