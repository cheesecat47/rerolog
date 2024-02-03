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
docker compose up -d --build frontend
```

## NGINX

```bash
docker compose up -d nginx
```
