# Lab Synopsis

Create a stack of AWS infrastructure from TWO Cloud Formation templates. Be able to store the templates in S3 & launch
from the AWS CLI. After testing the created infrastructure tear down the stack automatically with Cloud Formation.

### What to build

**Build all Cloud Formation templates in YAML – ignore any references to JSON**

One of the labs in the AWS Academy Cloud Foundations content provided a good basic setup for production websites sitting
“behind” a load balancer in a single region production website deployment. We’ll make use of that basic setup for this
design. You’ll want to build this with TWO Cloud Formation templates – we are making a few modifications from the
original lab:

* Before you start constructing the Cloud Formation templates you’ll need to build a custom AMI to use as your launch
  AMI for the two webservers built in template #2. See the section in this document called “Custom Web Server AMI Build”
  for tips on building that.
* You WILL need to make the AMI PUBLIC after you create it so I can access it for grading when I run your templates
* AWS Cloud Formation templates you will construct:

Cloud Formation Template #1 (see the YAML outline document for a starting point)

MUCH of the code needed for this template you can pull from the Cloud Formation Lab template you constructed! No need to
reinvent the wheel.

    a. VPC 10.0.0.0/16

    b. Internet Gateway

    c. 4 Subnets built in two availability zones:

        i. Public Subnet 1 – 10.0.1.0/24

        ii. Public Subnet 2 – 10.0.2.0/24

        iii. Private Subnet 1 – 10.0.3.0/24

        iv. Private Subnet 2 – 10.0.4.0/24

    d. Appropriate Security Groups (2 total)

        i. One Security Group for the Application Load Balancer

        ii. One Security Group used by both Web Instances

    e. You will export the values of the following from template #1 so you can import them into template #2: VPC ID, All Subnet IDs, Both Security Group IDs.

    i. Look at my example of how this works in the template #1 YAML outline under “Outputs”. Here we are exporting the VPC ID value into an export that will be named: “WhateverYouCalledYourStack-VPCId” Look at this export after template #1 runs in Cloud Formation in my example where my template is named “network”:

# Custom Web Server AMI Build

Before beginning construction of the Cloud Formation templates you’ll need to build out an EC2 Instance to create an AMI
image from that will be used to launch the web instances shown in the diagram. Once you have a working AMI saved out you
can terminate this initial EC2 instance.

Make sure to set the permissions on the AMI to public so I can see it. The construction of the AMI will result in a
small EBS snapshot that will be saved in your account - this is necessary since it backs the AMI - don’t delete it.

User data to launch your initial webserver with to install the website. This will NOT have to be repeated for your Web
Servers launched with the Cloud Formation template because it will already be incorporated into the AMI. Run the
newami.sh
