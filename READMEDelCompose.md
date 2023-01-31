# tenpo-challenge

Desiciones de diseño:

- WebClient en vez de RestTemplate porque están deprecandolo
- No usar cache distribuida porque es un overkill, en vez de eso usar cache del webClient en memoria
- Tambien se puede hablar de que, si la api externa devolviera la cookie "Cache-Control" con un "max-age=0" adecuando, se podria implementar
- una cache que no sobreescriba eso
- Hablar de AOP AspectJ, WebInterceptor y Servlet Filters
- Hablar de que iba a usar el filter para todos los urls, pero iba a usar un converter convirtiendolo a string y me pasaba de 255 muy rapido
- Hablar de que usé el rate limiter ese porque podria extenderse a un rate limiter distribuido

- PENDIENTES:
  - Usar una api de random que no sea estatica (pagar https://mocki.io/docs)
  - Separar dockerfile + .env a carpeta separada
  - Dejar de builder la imagen en el dockerfile y pasarlo a una imagen en alguna registry
  - Documentar bien las configuraciones:
    - El archivo application.yml => Tiene la configuracion común a prod (docker o docker-compose) y local (intellij o desde consola)
    - El archivo application-local.yml => Tiene la config solo de local y no debería ser commiteado
    - El archivo .env del docker-compose => Tiene la config solo de prod y no debería ser commiteado
  - Borrar los templates y documentar como crear el .env y el application-local.yml
  - Collecction de postman
  - El async no se como anda
  - Hacer andar el volumen de docker
  - Revisar la division del bigDecimal
  - Hacer todos los tests