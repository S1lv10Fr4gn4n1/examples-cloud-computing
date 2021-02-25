import redis
import os
import json

def handler(event, context):
    print("Received event: " + json.dumps(event, indent=2))
    redis_host = os.environ.get("REDIS_HOST")
    redis_port = 6379
    redis_password = ""

    r = redis.StrictRedis(
        host=redis_host,
        port=redis_port,
        password=redis_password,
        decode_responses=True
    )

    name = event.get("name")

    if event.get("body"):
        name = json.loads(event["body"]).get("name")


    if name:
        redis_successful_set = r.set("name", name)
        if redis_successful_set:
            return {
                "statusCode": 200,
                "body": "Success! {name} was written to Redis".format(name=name.capitalize())
            }
        else:
            return {
                "statusCode": 500,
                "body": "Oops! Could not write {name} to Redis".format(name=name.capitalize())
            }


    return {
        "statusCode": 200,
        "body": "Hello {name} nice to meet you".format(name=r.get("name").capitalize())
    }