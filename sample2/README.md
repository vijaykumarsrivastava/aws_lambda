# AWS Lambda Sample 2

# Create the Lambda which write the data into dynamoDB.

#### Step 1 : Create IAM role 

1. Create Role
2. AWS Service
3. Use case : Lambda
4. Next
5. Permissions policies : AmazonDynamoDBFullAccess
6. Next
7. Give the role name
8. Create role

#### Step 2 : Create the DynamoDB table (This table name will use in lambda function code)

1. Create table
2. Give table name : UserData (give the same name in function code)
3. Partition key : id  (keep type as a string only)
4. Create table

#### Step 3 : Build lambda jar

1. Checkout https://github.com/vijaykumarsrivastava/aws_lambda/tree/main/sample2/WriteOnDynamoDB code.
2. mvn clean package shade:shade
3. Three jar file will be created.
3.1. original-WriteOnDynamoDB-0.0.1-SNAPSHOT.jar
3.2. WriteOnDynamoDB-0.0.1-SNAPSHOT-shaded.jar
3.3. WriteOnDynamoDB-0.0.1-SNAPSHOT.jar

#### Step 4 : Create lambda function

1. Create function
2. Give function name
3. Runtime : Java 8
4. Change default execution role : Use an existing role (select that role which we created in step 1)
5. Create function
6. Runtime settings -> Edit -> Handler : com.vijay.aws.lambda.sample2.SaveUserHandler::handleRequest
7. Code -> Upload from -> jar -> WriteOnDynamoDB-0.0.1-SNAPSHOT-shaded.jar -> Upload -> Save

#### Step 5 : Test lambda function

1. Test
2. Replace Event JSON with following json.

```
{
  "id": "1",
  "firstName": "John",
  "lastName": "Doe",
  "age": 30,
  "address": "United States"
}
```

3. Test
