package com.kltn.auth_service.ghn;

import com.kltn.auth_service.ghn.dto.GhnOrderRequest;
import com.kltn.auth_service.ghn.dto.GhnOrderResponse;
import com.kltn.auth_service.ghn.dto.GhnPrintOrderRequest;
import com.kltn.auth_service.ghn.dto.GhnTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;


@Service
public class GhnApiService {

    @Value("${ghn.api.token}")
    private String token;

    @Value("${ghn.api.shopId}")
    private String shopId;

    @Value("${ghn.api.url}")
    private String ghnApiUrl;

    private final RestTemplate restTemplate;

    public GhnApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GhnOrderResponse createOrder(GhnOrderRequest request) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Token", token);
        headers.set("ShopId", shopId);

        HttpEntity<GhnOrderRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GhnOrderResponse> response = restTemplate.exchange(
                ghnApiUrl + "/shipping-order/create",
                HttpMethod.POST,
                entity,
                GhnOrderResponse.class
        );

        return response.getBody();
    }

    public GhnTokenResponse generatePrintOrderToken(List<String> orderCodes) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.set("Token", token);

        GhnPrintOrderRequest request = new GhnPrintOrderRequest();
        request.setOrderCodes(orderCodes);

        HttpEntity<GhnPrintOrderRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<GhnTokenResponse> response = restTemplate.exchange(
                ghnApiUrl + "/a5/gen-token",
                HttpMethod.POST,
                entity,
                GhnTokenResponse.class
        );

        return response.getBody();
    }
}
