## Enunciado
En una galaxia lejana, existen tres civilizaciones: Vulcanos, Ferengis y Betasoides.

Dominan la predicción del clima mediante un complejo sistema informático:
* El planeta Ferengi se desplaza con una velocidad angular de 1 grado por día en sentido
  horario. Su distancia con respecto al sol es de 500km
* El planeta Betasoide se desplaza con una velocidad angular de 3 grados por día en sentido
  horario. Su distancia con respecto al sol es de 2000km.
* El planeta Vulcano se desplaza con una velocidad angular de 5 grados por día en sentido
  anti-horario. Su distancia con respecto al sol es de 1000km.

Todas las órbitas son circulares.
Cuando los tres planetas están alineados entre sí y a su vez alineados con respecto al sol, el
sistema solar experimenta un período de sequía.
Cuando los tres planetas no están alineados, forman entre sí un triángulo. Es sabido que en el
momento en el que el sol se encuentra dentro del triángulo, el sistema solar experimenta un
período de lluvia, teniendo éste, un pico de intensidad cuando el perímetro del triángulo está en
su máximo.
Las condiciones óptimas de presión y temperatura se dan cuando los tres planetas están
alineados entre sí pero no están alineados con el sol.

Realizar:                                                                                                         
Un programa informático para poder predecir en los próximos 10 años:
1. ¿Cuántos períodos de sequía habrá?
2. ¿Cuántos períodos de lluvia habrá y qué día será el pico máximo de lluvia?
3. ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?

Generar un modelo de datos con las condiciones de todos los días hasta 10 años en adelante
utilizando un job para calcularlas. Generar una API REST la cual devuelve en formato JSON la
condición climática del día consultado.

Generar y adjuntarla documentación considerada necesaria e importante.

Requerido: utilizar Java/Spring


Extra (deseable – no requerido):

● Hostear el modelo de datos y la API REST en un cloud computing libre y enviar la URL
para consultar el clima de un día en particular


## Solución ##
* La solución se puede lograr ver al ejecutar la clase Main y consumiendo los siguientes EndPoints:
1. ("/clima/prediction") Te crea y alcemna en la BDD las predicciones de los siguientes 10 años
2. ("/clima/periodos/sequia") ¿Cuántos períodos de sequía habrá? 
3. ("/clima/periodos/periodos/lluvia") ¿Cuántos períodos de lluvia habrá?
4. ("/clima/periodos/lluvia/diaPicoMax") ¿Qué día será el pico máximo de lluvia?
5. ("/clima/periodos/condicionOptima") ¿Cuántos períodos de condiciones óptimas de presión y temperatura habrá?
6. ("/clima/fecha") Buscar fecha y clima del día consultado
