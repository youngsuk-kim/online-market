from fastapi import FastAPI
from services.kafka_consumer import start_kafka_consumer

app = FastAPI()


@app.get("/")
def read_root():
    return {"message": "Welcome to the FastAPI application"}


@app.on_event("startup")
async def startup_event():
    start_kafka_consumer()
