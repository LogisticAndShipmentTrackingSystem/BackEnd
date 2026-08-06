from fastapi import FastAPI
import uvicorn
from app.api.routes import router
from app.config.settings import settings

app = FastAPI(
    title="LogiAssist AI Service",
    version="1.0.0"
)

app.include_router(router)

if __name__ == "__main__":
    print("Origin call")
    uvicorn.run("main:app", port=settings.SERVICE_PORT, reload=True)