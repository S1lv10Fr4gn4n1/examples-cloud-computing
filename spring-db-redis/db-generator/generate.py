#!/bin/bash
# psql -U postgres -d database_name -c "SELECT c_defaults  FROM user_info WHERE c_uid = 'testuser'"

import os
import uuid
import concurrent.futures
import logging
import time
import random

names_file = open('names.txt', 'r')
names = [name.replace('\n', '') for name in names_file.readlines()] 

# total of 18239 names
names = names * 17
# total over 300k names 
print("total", len(names))

random.shuffle(names)
last_name_list = list(names)

random.shuffle(names)
first_name_list = list(names)

format = "%(asctime)s: %(message)s"
logging.basicConfig(format=format, level=logging.INFO, datefmt="%H:%M:%S")

email_domains = ["gmail.com", "hotmail.com", "live.com", "apple.com", "amazon.com"]

def insert_thread(first_name, last_name):
    name = first_name + " " + last_name
    id = uuid.uuid3(uuid.NAMESPACE_OID, name)
    email = "{}@{}".format(name.replace(" ", ".").lower(), email_domains[random.randint(0, len(email_domains))])
    
    logging.info("+ {}, {}, {}".format(id, name, email))
    
    command = "insert into user1 (id, name, email) values  ('{}', '{}', '{}')".format(id, name, email)
    os.system("psql postgresql://user:user@localhost:5432/user -c \"{}\" > /dev/null".format(command))
    
with concurrent.futures.ThreadPoolExecutor(max_workers=10) as executor:
    executor.map(insert_thread, first_name_list, last_name_list)

print("Finished")