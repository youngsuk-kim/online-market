def detect_anomaly(comment: str) -> bool:
    # 간단한 키워드 기반 탐지 로직 예제
    anomalies = ["badword", "spam"]
    return any(anomaly in comment for anomaly in anomalies)
