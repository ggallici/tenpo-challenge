# tenpo-challenge

## Setup para desarrollo

Clonar el repositorio y, dentro de la carpeta `src/main/resources`, crear un archivo `application-local.yml`.
Este debe contener las configuraciones propias del ambiente de desarrollo, las cuales son complementarias a las configuraciones default ubicadas en el archivo `application.yml`.

Ej de configuración:

```yaml
spring:
    datasource:
        url: jdbc:postgresql://localhost:5432/test
        username: test
        password: test
    jpa:
        hibernate:
            ddl-auto: create

custom:
    rest.service:
        services:
            percentage:
                retries: 3
    cache.service:
        services:
            percentage:
                ttl: 30
    rate.limit.service:
        max-rpm: 3
```
