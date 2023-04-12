package com.vijay.aws.lambda.sample2;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification.S3EventNotificationRecord;

public class SaveFileInfoHandler implements RequestHandler<S3Event, FileInfoResponse> {

    private AmazonDynamoDB amazonDynamoDB;
    private String DYNAMODB_TABLE_NAME = "FileInfo";
    private Regions REGION = Regions.AP_SOUTH_1;
    
    public FileInfoResponse handleRequest(S3Event event, Context context) {
        
    	this.initDynamoDbClient();

    	for(S3EventNotificationRecord s3EventNotification: event.getRecords()) {
    		FileInfo fileInfo = prepareFileInfo(s3EventNotification);
    		persistData(fileInfo);
    	}
        FileInfoResponse userResponse = new FileInfoResponse();
        userResponse.setMessage("Saved Successfully!!!");
        return userResponse;
    }
	
    
    private FileInfo prepareFileInfo(S3EventNotificationRecord s3EventNotification) {
    	FileInfo fileInfo = new FileInfo();
		fileInfo.setBucket(s3EventNotification.getS3().getBucket().getName());
		fileInfo.setEvent(s3EventNotification.getEventName());
		fileInfo.setEventTime(s3EventNotification.getEventTime().toString());
		fileInfo.setObject(s3EventNotification.getS3().getObject().getKey());
		fileInfo.setSize(s3EventNotification.getS3().getObject().getSizeAsLong().toString());
		return fileInfo;
	}


	private void persistData(FileInfo fileInfo) throws ConditionalCheckFailedException {

        Map<String, AttributeValue> attributesMap = new HashMap<>();
        attributesMap.put("id", new AttributeValue(UUID.randomUUID().toString()));
        attributesMap.put("Bucket", new AttributeValue(fileInfo.getBucket()));
        attributesMap.put("Object", new AttributeValue(fileInfo.getObject()));
        attributesMap.put("Size", new AttributeValue(String.valueOf(fileInfo.getSize())));
        attributesMap.put("Event", new AttributeValue(fileInfo.getEvent()));
        attributesMap.put("EventTime", new AttributeValue(fileInfo.getEventTime()));

        amazonDynamoDB.putItem(DYNAMODB_TABLE_NAME, attributesMap);
    }
    
	private void initDynamoDbClient() {
        this.amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
            .withRegion(REGION)
            .build();
    }
}
