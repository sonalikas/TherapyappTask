package com.aws.lambda.therapy.apis;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.ScanRequest;
import com.amazonaws.services.dynamodbv2.model.ScanResult;
import java.util.List;
import java.util.stream.Collectors;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.therapy.model.Therapy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class GetTherapyLambda {

	ObjectMapper objectMapper = new ObjectMapper();

	
	public APIGatewayProxyResponseEvent getTherapy(APIGatewayProxyRequestEvent request)
			throws JsonProcessingException, JsonProcessingException {

		AmazonDynamoDB dynamoDB = AmazonDynamoDBClientBuilder.defaultClient();
		ScanResult scanResult = dynamoDB.scan(new ScanRequest().withTableName(System.getenv("THERAPY_TABLE")));
	List<Therapy> therapy	= scanResult
			.getItems().stream().map(item -> new Therapy(Integer.parseInt(item.get("TherapistId").getN()),
						item.get("TherapistName").getS(), item.get("TherapyName").getS(),
						item.get("TherapyDescription").getS(), 0))
				.collect(Collectors.toList());

		String jsonOutput = objectMapper.writeValueAsString(therapy);
		return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody(jsonOutput);

	}
}
