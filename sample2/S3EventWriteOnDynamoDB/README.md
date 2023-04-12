# AWS Lambda Sample 2

# Create the Lambda which get triggered by s3 event and write the data into dynamoDB.

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
2. Give table name : FileInfo (give the same name in function code)
3. Partition key : id  (keep type as a string only)
4. Create table

#### Step 3 : Create S3 bucket
1. Create the S3 bucket and give any name. (This name will use while assigning lambda trigger, lambda function code don't need this name.)
2. Unchecked : Block all public access
3. acknowledge warraning
4. create bucket

#### Step 4 : Build lambda jar

1. Checkout https://github.com/vijaykumarsrivastava/aws_lambda/tree/main/sample2/S3EventWriteOnDynamoDB code.
2. mvn clean package shade:shade
3. S3EventWriteOnDynamoDB-0.0.1-SNAPSHOT.jar file will get generated.

#### Step 5 : Create lambda function

1. Create function
2. Give function name
3. Runtime : Java 8
4. Change default execution role : Use an existing role (select that role which we created in step 1)
5. Create function
6. Add Trigger -> Select a source : s3 -> select the bucket that we created above -> Event types : All object create events -> acknowledge -> Add
6. Code -> Runtime settings -> Edit -> Handler : com.vijay.aws.lambda.sample2.SaveFileInfoHandler::handleRequest
7. Code -> Upload from -> jar -> S3EventWriteOnDynamoDB-0.0.1-SNAPSHOT.jar -> Upload -> Save

#### Step 5 : Test lambda function

1. Upload file in s3 bucket
2. Entry should created in DynamoDB .. You can check attribute such as file, event type, bucket name and etc.
 