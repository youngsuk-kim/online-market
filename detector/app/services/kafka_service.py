from kafka import KafkaProducer


def get_kafka_producer(bootstrap_servers: str):
    return KafkaProducer(bootstrap_servers=bootstrap_servers)


def send_comment_to_kafka(producer, topic: str, comment: str):
    producer.send(topic, comment.encode('utf-8'))
    producer.flush()
