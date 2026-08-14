from app.config.settings import settings

from app.providers.chat_model_provider import ChatModelProvider

from app.providers.registry import MODEL_REGISTRY

class ProviderFactory:
    @staticmethod
    def get_provider():
        # AI_PROVIDER is name of service provider.
        # used as ENUM.
        provider = settings.AI_PROVIDER

        # if not found then raise an exception.
        if provider not in MODEL_REGISTRY:
            raise Exception(f"{provider} is not registered.")
        
        model = MODEL_REGISTRY[provider]()

        return ChatModelProvider(
            model,
            provider
        )