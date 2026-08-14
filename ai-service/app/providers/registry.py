from langchain_google_genai import ChatGoogleGenerativeAI
from langchain_groq import ChatGroq
from app.config.settings import settings

"""
This is a dictonary(dict) where all models are registered and ready to use all over the code.
key: "name of used AI provider"
data: lambda function which returns required model.
"""
MODEL_REGISTRY = {

    "gemini": lambda :
        ChatGoogleGenerativeAI(
            model=settings.MODEL_NAME,
            google_api_key=settings.GEMINI_API_KEY
        ),
    
    "groq": lambda :
        ChatGroq(
            api_key=settings.GROQ_API_KEY,
            model='llama-3.1-8b-instant'            
        )
}