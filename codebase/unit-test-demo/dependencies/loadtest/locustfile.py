import json
import sys
from locust import FastHttpUser, task, between

CONFIG_FILE = 'signin.json'

try:
    with open(CONFIG_FILE, "r") as f:
        API_CONFIGS = json.load(f)
except (FileNotFoundError, json.JSONDecodeError):
    print("ERROR: Invalid or missing LOCUST_CONFIG_FILE.")
    sys.exit(1)

# Ensure we have at least one API to test
if not API_CONFIGS:
    print("ERROR: No APIs defined in LOCUST_CONFIG_FILE.")
    sys.exit(1)


class LoadTest(FastHttpUser):
    wait_time = between(0, 1)
    host = 'http://app:8080'

    @task
    def hit_endpoints(self):
        """Loop through all APIs and trigger requests sequentially"""
        for api in API_CONFIGS:
            method = api["method"].upper()
            endpoint = api["endpoint"]
            headers = api.get("headers", {})
            payload = api.get("payload", {})

            if method == "GET":
                response = self.client.get(endpoint, headers=headers)
            elif method == "POST":
                response = self.client.post(endpoint, json=payload, headers=headers)
            elif method == "PUT":
                response = self.client.put(endpoint, json=payload, headers=headers)
            elif method == "DELETE":
                response = self.client.delete(endpoint, json=payload, headers=headers)
            elif method == "PATCH":
                response = self.client.patch(endpoint, json=payload, headers=headers)
            else:
                print(f"ERROR: Unsupported HTTP method {method}. Supported: GET, POST, PUT, DELETE, PATCH.")
                sys.exit(1)
