package com.aws.lambda.therapy.apis;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.therapy.model.Therapy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AddTherapistLambda {

	ObjectMapper objectMapper = new ObjectMapper();

	public APIGatewayProxyResponseEvent addTherapy(APIGatewayProxyRequestEvent request)
			throws JsonMappingException, JsonProcessingException {
		Therapy therapy = objectMapper.readValue(request.getBody(), Therapy.class);

		DynamoDB dynamoDB = new DynamoDB(AmazonDynamoDBClientBuilder.defaultClient());
		Table table = dynamoDB.getTable(System.getenv("THERAPY_TABLE"));
		Item item = new Item().withPrimaryKey("TherapistId", therapy.TherapistId)
				.withString("TherapistName", therapy.TherapistName).withString("TherapyName", therapy.TherapyName)
				.withString("TherapyDescription", therapy.TherapyDescription);
		table.putItem(item);
		return new APIGatewayProxyResponseEvent().withStatusCode(200)
				.withBody("Therapy Added Succsesfully");

	}
}
