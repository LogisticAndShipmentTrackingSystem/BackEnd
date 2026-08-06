from langchain.messages import HumanMessage
from langchain.messages import SystemMessage
from app.models.provider_models import ProviderResponse

class ChatModelProvider:
    def __init__(self, chat_model, provider_name):
        self.chat_model = chat_model
        self.provider_name = provider_name
    
    def invoke(self, system_prompt, user_prompt) -> ProviderResponse:
        response = self.chat_model.invoke(
            [
                SystemMessage(content=system_prompt),
                HumanMessage(content=user_prompt)
            ]
        )
        content = response.content

        if isinstance(content, str):
            text = content

        elif isinstance(content, list):
            text = "".join(block.get("text", "")
                for block in content
                if isinstance(block, dict))

        else:
            text = str(content)
        
        return ProviderResponse(
            text=text,
            provider=self.provider_name,
            model=self.chat_model.model 
        )