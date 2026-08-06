from app.models.chat_models import ChatRequest, ChatResponse
from app.providers.provider_factory import ProviderFactory
from app.prompts.shipment_prompt import ShipmentPromptBuilder

class ChatService: 
    
    def __init__(self):
        self.provider = ProviderFactory.get_provider()

    async def chat(self, request: ChatRequest) -> ChatResponse:
        system_prompt, user_prompt = ShipmentPromptBuilder.build(request)

        response = self.provider.invoke(system_prompt, user_prompt)

        return ChatResponse(
            answer=response.text,
            provider=response.provider,
            model=response.model
        )