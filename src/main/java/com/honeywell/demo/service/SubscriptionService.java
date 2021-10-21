package com.honeywell.demo.service;

import com.honeywell.demo.dto.OAuthResponse;
import com.honeywell.demo.entity.Subscription;
import com.honeywell.demo.exception.ApiExecption;
import com.honeywell.demo.repository.SubscriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
public class SubscriptionService {

	@Autowired
	private SubscriptionRepository subscriptionRepository;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	Environment env;

	public List<Subscription> getAllSubscriptions() {
		return subscriptionRepository.findAll();
	}

	public Subscription createSubscription(Subscription subscription) {
		return subscriptionRepository.save(subscription);
	}

	public Object subscription() {
		HttpHeaders headers = new HttpHeaders();
		String accessToken = generateAccessToken();
		headers.add("Authorization", "Bearer " + accessToken);
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
		HttpEntity<String> request = new HttpEntity<String>(headers);
		String url = env.getProperty("subscriptionURL") + "?tails=N922H,N966H&service=cabin&userid=domuser30";
		Object response = null;
		try {
			response = restTemplate.exchange(url, HttpMethod.GET, request, Object.class).getBody();
		} catch(RuntimeException e) {
			throw new ApiExecption(e.getMessage());
		}
		return response;

	}

	public String generateAccessToken() {
		String accessToken = "";

		String clientId = env.getProperty("client_id");
		String clientSecret = env.getProperty("client_secret");
		String url = env.getProperty("accessTokenURL");

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_FORM_URLENCODED));
		headers.setBasicAuth(clientId, clientSecret);

		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("grant_type", "client_credentials");

		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);

		ResponseEntity<OAuthResponse> token = restTemplate.exchange(url, HttpMethod.POST, request, OAuthResponse.class);

		accessToken = token.getBody().getAccess_token();

		return accessToken;
	}

}
