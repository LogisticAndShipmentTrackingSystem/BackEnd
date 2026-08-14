from fastapi import APIRouter
from datetime import datetime
from app.models.chat_models import ChatRequest
from app.services.chat_service import ChatService

router  = APIRouter(
    prefix="/chat",
    tags=["AI chat"]
)

service = ChatService()

@router.post("")
def chat(request: ChatRequest):

    return service.chat(request)

@router.get("/health")
def health():
    
    return {
        "status": "UP",
        "data": "Working all OK.",
        "time": datetime().now()
    }