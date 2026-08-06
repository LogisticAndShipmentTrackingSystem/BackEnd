from pydantic import BaseModel, Field

class UserContext(BaseModel):
    name: str = Field(..., min_length=2, max_length=50)
    city: str = Field(..., min_length=2, max_length=25)
    preferred_language: str = Field(default="English")


class OrderContext(BaseModel):
    tracking_id: str = Field(min_length=5, max_length=30)
    status: str = Field(..., min_length=2)
    agent_name: str = Field(..., min_length=2)
    destination_city: str = Field(..., min_length=2)

class ChatRequest(BaseModel):
    question: str = Field(..., min_length=2, max_length=500)
    user: UserContext
    # order: OrderContext
    tracking_id: str

class ChatResponse(BaseModel):
    answer: str | None
    provider: str | None
    model: str