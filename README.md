# Taller 1 - Hello Android

## Información del Estudiante
- Nombre: Juan Sebastian Ropero Amado
- Grupo: E191
- Fecha: 05/03/2026

## Respuestas

### 1. Función del AndroidManifest.xml
El archivo AndroidManifest.xml es el archivo de configuración central de toda aplicación Android. 
Le declara al sistema operativo todo lo que necesita saber sobre la app antes de ejecutarla: 
el nombre del paquete que la identifica de forma única, todas las Activities, Services y Receivers que la componen, 
los permisos que solicita al usuario (como acceso a cámara o internet), el ícono y tema visual, 
y cuál Activity es el punto de entrada (LAUNCHER). Sin este archivo, Android no puede instalar ni ejecutar la aplicación.

### 2. Diferencia entre activity_main.xml y MainActivity.kt
Son dos capas del mismo patrón de diseño: activity_main.xml define qué se ve (la interfaz), mientras que MainActivity.kt define qué hace (la lógica). El XML describe la estructura visual usando elementos como TextView, Button y sus atributos de posición, tamaño y color. El archivo Kotlin lee esos elementos en tiempo de ejecución mediante findViewById, y les asigna comportamiento: responder a clics, actualizar texto, mostrar mensajes, etc. Esta separación entre vista y lógica hace el código más organizado y mantenible.

### 3. Gestión de recursos en Android
Android gestiona los recursos limitados del dispositivo (memoria RAM, CPU, batería) mediante un ciclo de vida controlado de las Activities. El sistema puede pausar, detener o destruir una Activity cuando necesita liberar memoria para otra tarea, notificando al desarrollador con métodos como onPause(), onStop() y onDestroy(). Además, gestiona los recursos de la app (imágenes, textos, layouts) desde la carpeta res/, separándolos del código para poder adaptarlos automáticamente a diferentes tamaños de pantalla, idiomas e configuraciones de hardware sin modificar la lógica.

### 4. Aplicaciones famosas que usan Kotlin
Netflix — usa Kotlin en su app Android para la gestión de su interfaz de streaming y personalización de contenido.
Duolingo — migró su app Android completamente a Kotlin, aprovechando las corrutinas para manejar llamadas a la red de forma eficiente.
Pinterest — adoptó Kotlin para reducir los errores de NullPointerException y mejorar la legibilidad del código de su feed visual.

## Capturas de Pantalla

![Captura del emulador](docs/captura_emulador.png)
"# andorid-mobile" 
