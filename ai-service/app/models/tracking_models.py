from typing import List, Optional

from datetime import datetime
from decimal import Decimal
from uuid import UUID

from pydantic import BaseModel, Field

class TrackingResponse(ApiResponse[OrderDetails]):
    pass

class OrderDetails(BaseModel):

    id: UUID

    tracking_number: str = Field(alias="trackingNumber")

    customer_id: UUID = Field(alias="customerId")

    pickup_address: str = Field(alias="pickupAddress")

    delivery_address: str = Field(alias="deliveryAddress")

    receiver_name: str = Field(alias="receiverName")

    receiver_phone: str = Field(alias="receiverPhone")

    package_type: str = Field(alias="packageType")

    weight: float

    amount: Decimal

    payment_status: str = Field(alias="paymentStatus")

    order_status: str = Field(alias="orderStatus")

    assigned_agent_id: UUID | None = Field(
        default=None,
        alias="assignedAgentId",
    )

    created_at: datetime = Field(alias="createdAt")