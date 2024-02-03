# rerolog deploy guide

## DB

```bash
docker compose up -d db
```

## API

```bash
docker compose up -d --build api
```

## FE

```bash
docker compose up -d --build frontend && docker compose stop frontend && docker compose rm -f frontend
```

## NGINX

```bash
docker compose -f compose.yml -f compose.dev.yml up -d nginx
```
