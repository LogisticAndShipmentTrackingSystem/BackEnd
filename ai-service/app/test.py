from google import genai
from config.settings import settings
from google.genai import types

import sounddevice as sd
import soundfile as sf
import io
import numpy as np

client = genai.Client(api_key=settings.GEMINI_API_KEY)

transcript_interact = client.interactions.create(
    model=settings.MODEL_NAME,
    input="""Generate short prompted 50 words speech on how AI is working today
    in english as a student giving good speech on stage. speaker name is Saurabh"""
)

transcript = transcript_interact.output_text

print("-"*15, f"\n{transcript}", end="\n---")

response = client.models.generate_content(
    model="gemini-2.5-flash-preview-tts",
    contents=transcript,
    config=types.GenerateContentConfig(
        response_modalities=["AUDIO"],
        speech_config=types.SpeechConfig(
            voice_config=types.VoiceConfig(
                prebuilt_voice_config=types.PrebuiltVoiceConfig(
                    voice_name="Rasalgethi"
                )
            )
        ),
    ),
)

part = response.candidates[0].content.parts[0]

pcm_bytes = part.inline_data.data

audio = np.frombuffer(pcm_bytes, dtype=np.int16)

audio = audio.astype(np.float32) / 32768.0

sd.play(audio, samplerate=24000)
sd.wait()

print("Done!")