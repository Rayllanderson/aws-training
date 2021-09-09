import json
import boto3

s3 = boto3.resource('s3')

def lambda_handler(event, context):
    buckets = [bucket.name for bucket in s3.buckets.all()]
    return {
      'statusCode': 200,
      'body': buckets
    }