FROM wiremock/wiremock:3.2.0-alpine

COPY ./requests.json /home/wiremock/mappings/requests.json

ENTRYPOINT ["/docker-entrypoint.sh", "--global-response-templating", "--disable-gzip", "--verbose"]