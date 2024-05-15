import time

from kafka import KafkaConsumer
from app.core.config import settings


def start_kafka_consumer():
    consumer = KafkaConsumer(
        settings.kafka_input_topic,
        bootstrap_servers=settings.kafka_bootstrap_servers,
        auto_offset_reset='earliest',
        enable_auto_commit=False,  # 자동 커밋을 끕니다.
        group_id='test-group',
    )

    for message in consumer:
        comment = message.value.decode('utf-8')
        print(comment)

