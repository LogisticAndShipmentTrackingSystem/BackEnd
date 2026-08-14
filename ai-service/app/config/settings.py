from dotenv import load_dotenv

import os

load_dotenv()

class Settings:

    APP_NAME: str = "AI-SERVICE"

    HOST: str = "localhost"

    EUREKA_SERVER: str = "http://localhost:8761/eureka"

    SERVICE_PORT: int = int(os.environ["SERVICE_PORT"])
    AI_PROVIDER: str = os.getenv("AI_PROVIDER").lower()

    GEMINI_API_KEY: str = os.getenv("GEMINI_API_KEY")

    GROQ_API_KEY: str = os.getenv("GROQ_API_KEY")

    MODEL_NAME: str = os.getenv("MODEL_NAME")

    API_GATEWAY_URL: str = os.getenv("SPRING_BOOT_BASE_URL")

    REQUEST_TIMEOUT: int = int(os.getenv("HTTP_TIMEOUT", "30"))

settings = Settings()