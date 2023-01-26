# tenpo-callenge

Desiciones de diseño:

- WebClient en vez de RestTemplate porque están deprecandolo
- No usar cache distribuida porque es un overkill, en vez de eso usar cache del webClient en memoria
- Tambien se puede hablar de que, si la api externa devolviera la cookie "Cache-Control" con un "max-age=0" adecuando, se podria implementar
- una cache que no sobreescriba eso

- // https://www.random.org/clients/http/api/ use esa api porque tenia mas o menos alguna docu.