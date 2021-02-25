#!/usr/bin/env python3

from aws_cdk import core
from stacks.vpc_stack import VPCStack
from stacks.s3_stack import S3Stack

app = core.App()
vpc_stack = VPCStack(app, "vpc")
s3_stack = S3Stack(app, "s3")

app.synth()