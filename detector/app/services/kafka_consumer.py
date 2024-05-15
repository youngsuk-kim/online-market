from kafka import KafkaConsumer, KafkaProducer
from app.services.anomaly_detection_service import detect_anomaly
from app.core.config import settings


def start_kafka_consumer():
    consumer = KafkaConsumer(
        settings.kafka_input_topic,
        bootstrap_servers=settings.kafka_bootstrap_servers,
        auto_offset_reset='earliest',
        enable_auto_commit=True,
        group_id='comment-anomaly-group'
    )
    producer = KafkaProducer(bootstrap_servers=settings.kafka_bootstrap_servers)

    for message in consumer:
        comment = message.value.decode('utf-8')
        is_anomalous = detect_anomaly(comment)
        result = f"Comment: {comment}, Anomalous: {is_anomalous}"
        producer.send(settings.kafka_output_topic, result.encode('utf-8'))
        producer.flush()
