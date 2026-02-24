Taller 1 - Hello Android

Información del Estudiante
Nombre: Nestor David Garcia Rondon
Código: 2021-02-E
Documento: 1095298886
Fecha: 23/02/2026


Respuestas
1. Función del AndroidManifest.xml
Es como el archivo de configuración principal de una aplicación Android que "lee" como está estructurada una app y sirve para cosas como:
 - Definir permisos como de cámara, acceso a internet, etc.
 - Declarar las actividades que tiene la app, por ejemplo una pantalla de inicio, una pantalla de login, una pantalla de perfil, etc.
 - Establecer cual es la actividad principal que se abre cuando se inicia la app.
 - Indicar configuraciones generales como el nombre de la aplicación, color, tema, etc.

2. Diferencia entre activity_main.xml y MainActivity.kt

La diferencia principal es:

activity_main.xml

Es el archivo de la interfaz gráfica por decirlo así. Se escribe en XML y define la apariencia o se diseña lo que el usuario ve en pantalla:
 - Botones
 - Textos
 - Imágenes
 - Layouts

MainActivity.kt

Es el archivo donde se escribe la lógica del programa en Kotlin. Aquí se define:
 - Qué pasa cuando se presiona un botón
 - Cómo cambian los textos
 - Cómo responde la aplicación a las acciones del usuario
 - Que acción ejecuta al hacer algo.

Se podría decir en forma general que activity_main.xml es lo visual y MainActivity.kt es la lógica de funcionamiento. 

3. Gestión de recursos en Android
Android administra los recursos del dispositivo mediante un sistema de gestión de memoria y procesos controlado por el sistema operativo y el Activity Manager. Cuando la memoria RAM comienza a escasear, el sistema puede cerrar aplicaciones que estén en segundo plano para liberar recursos y mantener el rendimiento del dispositivo.

Además, utiliza el ciclo de vida de las Activities (métodos como onPause(), onStop() y onDestroy()) para saber cuándo una aplicación está activa, en segundo plano o puede ser finalizada. Esto permite optimizar el uso de batería, CPU y memoria de manera automática.

4. Aplicaciones famosas que usan Kotlin
 - Netflix
 - Duolingo
 - Uber




