FROM python:3.13-bookworm

WORKDIR /loadtest
COPY requirements.txt ./
RUN pip install -r requirements.txt
COPY locustfile.py ./
COPY *.json ./

CMD ["locust"]
