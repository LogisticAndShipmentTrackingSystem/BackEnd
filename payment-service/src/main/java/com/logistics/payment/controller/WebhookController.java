package com.logistics.payment.controller;

import com.logistics.payment.service.WebhookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/webhooks")
@RequiredArgsConstructor
public class WebhookController {

	private final WebhookService webhookService;

	@PostMapping("/razorpay")
	public ResponseEntity<Void> handleWebhook(

			@RequestBody String payload,

			@RequestHeader("X-Razorpay-Signature") String signature) {

		webhookService.processWebhook(payload, signature);

		return ResponseEntity.ok().build();
	}
}
