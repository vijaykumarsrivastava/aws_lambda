# API Gateway access lambda for rest end point.

### Create the Lambda which gets triggered by api gateway.

#### Step 1 : Create lambda function

1. Create function
2. Give function name
3. Runtime : Python 3.7
4. Change default execution role : Use an existing role (select cloudwatch log permission role OR "Create a new role with basic Lambda permissions
")
5. Create function
6. Replace the following code in inline editor.
7. Save OR Deploy

```
import json

print('Loading function')

def lambda_handler(event, context):
	#1. Parse out query string params
	transactionId = event['queryStringParameters']['transactionId']
	transactionType = event['queryStringParameters']['type']
	transactionAmount = event['queryStringParameters']['amount']

	print('transactionId=' + transactionId)
	print('transactionType=' + transactionType)
	print('transactionAmount=' + transactionAmount)

	#2. Construct the body of the response object
	transactionResponse = {}
	transactionResponse['transactionId'] = transactionId
	transactionResponse['type'] = transactionType
	transactionResponse['amount'] = transactionAmount
	transactionResponse['message'] = 'Hello from Lambda land'

	#3. Construct http response object
	responseObject = {}
	responseObject['statusCode'] = 200
	responseObject['headers'] = {}
	responseObject['headers']['Content-Type'] = 'application/json'
	responseObject['body'] = json.dumps(transactionResponse)

	#4. Return the response object
	return responseObject


```

#### Step 2 : Create Amazon API Gateway
1. Amazon API Gateway -> REST API -> Build -> Choose the protocol :  REST -> Create new API : New AP -> API name* : transactions (it can be any thing) -> Endpoint Type : Regional -> Create API
2. Resource -> Actions : Create Resource -> Resource Name* : transactions (your choice) -> Create Resource
3. Resource -> Select resource that we created in step 2 -> Actions : Create Method -> Select GET method -> click on tick/ok -> Integration type :  Lambda Function -> Checked "Use Lambda Proxy integration" -> Lambda Region : ap-south-1 -> Lambda Function : search that lambda function - Save
4. Add Permission to Lambda Function : OK
5. Resource -> select api -> Actions : Deploy API -> Deployment stage : [New Stage] -> Stage name* : dev
7. Invoke URL: https://g5k3taojt0.execute-api.ap-south-1.amazonaws.com/dev

#### Test 
1. Append "transactions?transactionId=1&type=Sale&amount=20" in invoke url
2. Prepare url https://g5k3taojt0.execute-api.ap-south-1.amazonaws.com/dev/transactions?transactionId=1&type=Sale&amount=20
3. Hit step 2 url in browser, you will get response.


#### Clean up
1. API Gateway -> APIs -> select api -> Actions : Delete -> Delete
2. Lambda -> Functions -> select function -> Actions : Delete -> delete -> close
