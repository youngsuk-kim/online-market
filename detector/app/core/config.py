from pydantic_settings import BaseSettings


class Settings(BaseSettings):
    app_name: str = "FastAPI Project"
    kafka_bootstrap_servers: str
    kafka_input_topic: str
    kafka_output_topic: str

    class Config:
        env_file = ".env"


settings = Settings()
