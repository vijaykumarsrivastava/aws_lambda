# AWS Lambda Sample 1

# Create the Lambda which gets triggered by s3 and write the data into dynamoDB.

### Step 1 : Create IAM role 
1. Create Role
2. AWS Service
3. Use case : Lambda
4. Next
5. Permissions policies : AmazonDynamoDBFullAccess
6. Next
7. Give the role name
8. Create role

### Step 2 
1. Create the S3 bucket and give any name. (This name will use while assigning lambda trigger, lambda function don't need this name.)
2. Unchecked : Block all public access
3. acknowledge warraning
4. create bucket

### Step 3
Create the DynamoDB table (This table name will use in lambda function code)

1. Create table
2. Give table name : newtable (give the same name in function code)
3. Partition key : unique  (keep type as a string only)
4. Create table

### Step 4
Create lambda and add IAM role which we created in step 1 and add python 3.6 or 3.7 . In lambda function, write the following code. Cross check dynamoDB table name in function code. And the lambda trigger s3 and assiciate all_create_op event.
Note: Code should get deployed in lambda.

1. Create function
2. Give function name
3. Runtime : Python 3.7
4. Change default execution role : Use an existing role (select that role which we created in step 1)
5. Create function
6. Add trigger
7. Trigger configuration : s3
8. Select the bucket that we create above
9. Event types : All object create events
10. acknowledge "Recursive invocation"
11. Add
12. Click on code and edit "lambda_function.py" and replace this code with following code.
13. Click on "Deploy"




```
import boto3
from uuid import uuid4
def lambda_handler(event, context):
    s3 = boto3.client("s3")
    dynamodb = boto3.resource('dynamodb')
    for record in event['Records']:
        bucket_name = record['s3']['bucket']['name']
        object_key = record['s3']['object']['key']
        size = record['s3']['object'].get('size', -1)
        event_name = record ['eventName']
        event_time = record['eventTime']
        dynamoTable = dynamodb.Table('newtable')
        dynamoTable.put_item(
            Item={'unique': str(uuid4()), 'Bucket': bucket_name, 'Object': object_key,'Size': size, 'Event': event_name, 'EventTime': event_time})

```

### Test 
Go to S3 bucket and upload file. 
Entry should created in dynamoDB - DynamoDB -> Tables -> click on table -> click on "Explore table items" -> check in "Items returned"

### Clean up
1. DynamoDB -> Tables -> select table -> delete -> confirm -> delete
2. lambda -> select lambda -> Configuration -> Tiggers -> checked tigger -> delete --> confirm and delete -> close popup window
3. S3 -> Buckets -> checked bucket -> Empty -> confirm "permanently delete" -> Empty Bucket
4. S3 -> Buckets -> checked bucket -> Delete -> confirm "<bucketname>" -> Delete Bucket
5. AWS Lambda -> checked function -> Actions : Delete -> Confirm -> Delete -> Close popup
6. IAM -> Roles -> checked IAM role that we create above -> Delete -> Confirm -> Delete

