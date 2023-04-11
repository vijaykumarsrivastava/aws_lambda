package com.vijay.aws.lambda.sample2;

import java.util.HashMap;
import java.util.Map;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

public class SaveUserHandler implements RequestHandler<UserRequest, UserResponse> {

	    private AmazonDynamoDB amazonDynamoDB;

	    private String DYNAMODB_TABLE_NAME = "UserData";
	    private Regions REGION = Regions.AP_SOUTH_1;

	    public UserResponse handleRequest(UserRequest personRequest, Context context) {
	        this.initDynamoDbClient();

	        persistData(personRequest);

	        UserResponse userResponse = new UserResponse();
	        userResponse.setMessage("Saved Successfully!!!");
	        return userResponse;
	    }

	    private void persistData(UserRequest userRequest) throws ConditionalCheckFailedException {

	        Map<String, AttributeValue> attributesMap = new HashMap<>();

	        attributesMap.put("id", new AttributeValue(String.valueOf(userRequest.getId())));
	        attributesMap.put("firstName", new AttributeValue(userRequest.getFirstName()));
	        attributesMap.put("lastName", new AttributeValue(userRequest.getLastName()));
	        attributesMap.put("age", new AttributeValue(String.valueOf(userRequest.getAge())));
	        attributesMap.put("address", new AttributeValue(userRequest.getAddress()));

	        amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
	    }

	    private void initDynamoDbClient() {
	        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
	            .withRegion(REGION)
	            .build();
	    }
	}
