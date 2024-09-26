docker run --rm --name pg-docker \
  -e POSTGRES_PASSWORD=admin \
  -e POSTGRES_USER=admin \
  -e POSTGRES_DB=demo \
  -p 5430:5432 \
  postgres:13.4-buster