from pydantic import BaseModel


class ProviderResponse(BaseModel):

    text: str

    provider: str

    model: str