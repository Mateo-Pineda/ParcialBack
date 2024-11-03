Proyecto de Detección de ADN Mutante

Este proyecto es una API desarrollada en Java Spring Boot para verificar si una secuencia de ADN corresponde a un mutante y consultar estadísticas de las secuencias analizadas.

Requisitos y Configuración del Proyecto
 1. Clonar o actualizar el repositorio
    Clonar:
    git clone https://github.com/Mateo-Pineda/ParcialBack.git
    cd ParcialBack
    Actualizar:
    git pull origin main
 2. Configurar base de datos H2 para ejecución local
    Editar el archivo src/main/resources/application.properties para habilitar la configuración de la base de datos H2 en caso de querer realizar las pruebas localmente.
 3. Ejecutar el proyecto en local
    Iniciar la API en el entorno local con Maven:
    mvn spring-boot:run
    
####Uso de la API en Render####

La API está desplegada en Render y se puede acceder a través de la siguiente URL:

🔗 https://parcialback-rf6a.onrender.com

Utilizar los endpoints POST y GET en esta URL sin necesidad de ejecutar el proyecto localmente.
Endpoints Disponibles en Render:

1. Verificar ADN Mutante
    ############  Ruta: POST /mutant ############
    Descripción: Verifica si una secuencia de ADN corresponde a un mutante.
    Request Body: Enviar un JSON con la secuencia de ADN en formato NxN. Ejemplo(Existe la posibilidad de que ya exista dentro del sistema, pruebe con uno distinto en caso de estarlo):
   {
  "dna": ["ATGCGA", "CAGTGC", "TTATGT", "AGAAGG", "CCCCTA", "TCACTG"]
  }
200 OK: Si la secuencia corresponde a un mutante.
403 Forbidden: Si la secuencia no corresponde a un mutante.
   
2. Obtener estadísticas de ADN analizado
    ############ Ruta: GET /mutant/estadistica ############
    Descripción: Devuelve estadísticas de las secuencias de ADN analizadas.
    Respuesta: JSON con los resultados de la estadística (Ejemplo):
   {
  "count_mutant_dna": 40,
  "count_human_dna": 100,
  "ratio": 0.4
}
