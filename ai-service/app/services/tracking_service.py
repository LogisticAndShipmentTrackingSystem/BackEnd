import httpx
from app.config.settings import settings

TRACKING_SERVICE_URL = (f"{settings.API_GATEWAY_URL}/api/v1/orders/tracking")

async def get_tracking_details(tracking_id: str) -> OrderDetails:

    async with httpx.AsyncClient(timeout=settings.REQUEST_TIMEOUT) as client:
        response = await client.get(
            f"{TRACKING_SERVICE_URL}/{tracking_id}"
        )

        response.raise_for_status()

        api_response = TrackingResponse.model_validate(
            response.json()
        )

        return api_response.data