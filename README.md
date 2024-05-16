# Exercise

## Setup Guide

### Step 1

Copy env file for docker: `cp .env.example .env`

Use any editor to open the `.env` file and provide the values for `INFLUXDB_PASSWORD`, `INFLUXDB_ADMIN_TOKEN`, and `GRAFANA_PASSWORD`.

You can change other settings if you would like.


### Step 2

Copy application configuration file `cp application-dev.yml.example ./src/main/resources/application-dev.yml` 

Use any editor to open the `./src/main/resources/application-dev.yml` file.

Enter you API key provided by swop.cx to here

```yml
service:
  
  #...
  
  swop_cx:
    api_url: "https://swop.cx/rest"
    api_key: "[provide-swop-cx-api-key-here]"
```

Enter the InfluxDB admin token you have set on Step 1 to here

```yml
management:
  influx:
    metrics:
      export:
        
        #...
        
        token: "[value-set-for-INFLUXDB_ADMIN_TOKEN-in-the-env-file]"
```

> Note 1: If you have changed `APP_PORT` on step 1, you need to update the port number of `app.url` in this file as well


> Note 2: If you would like to use another exchange rate provider - "exchangerate-api.com", 
> you need to update the `exchange.provider` to be `exchangerate-api` 
> and enter its API key into `servcie.exchangerate_api.api_key`

### Step 3

run `docker compose up -d`

## Access the apps

|          |          url          | username |        password       |
|----------|:---------------------:|:--------:|:---------------------:|
| App      | http://localhost:8080 |          |                       |
| InfluxDB | http://localhost:8086 | admin    | [value-set-on-step-1] |
| Grafana  | http://localhost:3000 | admin    | [value-set-on-step-1] |

