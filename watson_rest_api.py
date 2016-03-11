# Test script for interacting with IBM's Watson
# by Bryan Wiltgen, started 1/11/2015, modified 1/13/16
# Uses the Requests library

# This file is just meant to represent a way to
# interact with Watson.  I'm learning this all
# with you all, so there may be misunderstandings
# in this file and it may not represent best practices.
# Please just view it as a starting point. :)
# -Bryan

# De-anonymized by Chris Purdy, 3/7/2016

import json
import requests

def askWatson(question):
    data={"question": {"questionText" : question}}

    username="gt2_administrator"
    password="bCze2OdC"

    url="https://watson-wdc01.ihost.com/instance/514/deepqa/v1/question"
    headers={"Content-type": "application/json",
             "Accept": "application/json",
             "X-SyncTimeout": "30"}

    print("Asking Watson: " + question + "\n")


    try:

        response = requests.post(url, data=json.dumps(data), auth=(username, password), headers=headers)

        # Print out the best answer
        
        jsonResponse = response.json()

        evidences = jsonResponse["question"]["evidencelist"]

        firstAnswer = evidences[0]["text"]


        print("Watson's answer: " + firstAnswer)

    except Exception as inst:
        
        print(inst.message)


def main():

    print("===== Starting ======\n\n")

    while True:
        user_input = raw_input("Enter a Question or Exit: ")
        if user_input == "Exit":
            break
        else:
            print("\n")
            askWatson(user_input)
            print("\n\n")

    print("\n\n===== Exiting ======")

if __name__ == '__main__':
    main()

