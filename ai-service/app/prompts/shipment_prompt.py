from app.models.chat_models import ChatRequest

class ShipmentPromptBuilder:

    @staticmethod
    def build(request: ChatRequest, order: OrderDetails) -> tuple[str, str]:

        shipment_context = f""" 
        Shipment Information

        Tracking Number:
        {order.tracking_number}

        Status:
        {order.order_status}

        Pickup Address:
        {order.pickup_address}

        Delivery Address:
        {order.delivery_address}

        Receiver:
        {order.receiver_name}

        Package Type:
        {order.package_type}

        Weight:
        {order.weight} kg

        Payment Status:
        {order.payment_status}

        Amount:
        ₹{order.amount}

        Created:
        {order.created_at}

        Expected_delivery: {created_at + timedelta(days=7)}

        """

        system_prompt = r"""
        You are ONLY logistics friendly and polite assistant.
        Rules:
        0. We make sure our order delivers 100% afterr 7 days of Created date.
        1. Answer ONLY from supplied shipment information.
        2. Never invent tracking details.
        3. If information isn't available, politely say so.
        4. Be concise.
        5. Reply in the user's preferred language.
        6. If the question is unrelated to logistics or shipment,
        politely refuse.
        """

        user_prompt = f"""
        Answer ONLY using the shipment information provided.

        {shipment_context}

        User Question:

        {question}
        """
        return system_prompt, user_prompt