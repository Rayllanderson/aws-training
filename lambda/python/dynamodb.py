import json
import boto3

dynamodb = boto3.resource('dynamodb')
table = dynamodb.Table('planets')

def lambda_handler_get_item(event, context):
  response = table.get_item(
    Key={
        'id': 'jupiter'
    }
  )
  return {
    'statusCode': 200,
    'body': response
  }


def lambda_handler_put_item(event, context):
  table.put_item(
      Item={
          'id': 'Saturn',
          'description': 'the sixth planet from the Sun and the second-largest planet in our solar system'
      }
  )
  return {
      'statusCode': 201,
      'body': 'Item added'
  }
