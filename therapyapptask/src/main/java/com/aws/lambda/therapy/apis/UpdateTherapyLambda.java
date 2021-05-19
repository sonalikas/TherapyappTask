package com.aws.lambda.therapy.apis;


import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.UpdateItemOutcome;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.aws.lambda.therapy.model.Therapy;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.amazonaws.services.lambda.runtime.Context;

public class UpdateTherapyLambda{

	public APIGatewayProxyResponseEvent updateTherapy(APIGatewayProxyRequestEvent request, Context context)
	throws JsonMappingException, JsonProcessingException {
			String id = request.getPathParameters().get("id");

			AmazonDynamoDB therapist = AmazonDynamoDBClientBuilder.defaultClient();   
        DynamoDB dynamoDB = new DynamoDB(therapist);
        Table table = dynamoDB.getTable(System.getenv("THERAPY_TABLE"));
        ObjectMapper objectMapper = new ObjectMapper();
        Therapy therapy = objectMapper.readValue(request.getBody(), Therapy.class);

        int getid = Integer.parseUnsignedInt(id);
       
		 
		 try {
			 if( getid == therapy.TherapistId) {	    	
				 
			        UpdateItemSpec updateItemSpec = new UpdateItemSpec().withPrimaryKey(id, therapy.TherapistId);
			        System.out.println("updating in process...");
			      UpdateItemOutcome outcome = table.updateItem(updateItemSpec);
	            System.out.println("Updatet successfully:\n" + outcome.getItem().toJSONPretty());
	            	
	            }else {
	   			 System.out.println("id not found");
	            }

	        }
	        catch (Exception e) {
	            System.err.println("Unable to update item: " );
	            System.err.println(e.getMessage());
	        }
		 return new APIGatewayProxyResponseEvent().withStatusCode(200).withBody("updeted seccesfully");
	}}
