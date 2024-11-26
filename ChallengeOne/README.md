# Simulador de Viajes Espaciales 🚀

## Descripción del Proyecto
Este proyecto es un simulador interactivo de viajes espaciales desarrollado en Java que permite a los usuarios planificar y ejecutar misiones espaciales a diferentes planetas del sistema solar. El programa ofrece una experiencia inmersiva donde los usuarios pueden seleccionar su destino, nave espacial y gestionar diversos eventos y situaciones que pueden surgir durante el viaje espacial.

## Características Principales
- 🌍 Selección de planetas destino (Marte, Júpiter, Saturno)
- 🛸 Diferentes naves espaciales disponibles
- ⚡ Sistema de eventos espaciales aleatorios
- 🛠️ Sistema de daños y reparaciones
- 📊 Cálculo detallado de recursos necesarios
- 📈 Estadísticas de viaje

## Estructura del Código

### Variables y Constantes Principales
- `planets`: Array con información detallada de los planetas disponibles
- `distances`: Distancias en millones de kilómetros desde la Tierra
- `spaceships`: Lista de naves espaciales disponibles
- `speeds`: Velocidades de cada nave en km/h
- `componentHealth`: Sistema de salud de los componentes de la nave
- `eventNames` y `eventDescriptions`: Eventos espaciales que pueden ocurrir

### Métodos Principales

#### `showMenu()`
- Muestra el menú principal de la aplicación
- Permite al usuario seleccionar entre diferentes opciones de navegación

#### `selectPlanet()`
- Permite al usuario seleccionar un planeta destino
- Muestra información detallada sobre cada planeta y su distancia

#### `selectSpaceship()`
- Permite seleccionar una nave espacial
- Incluye la configuración del número de pasajeros
- Inicializa los sistemas de la nave

#### `calculateResources()`
- Calcula los recursos necesarios para el viaje:
  - Combustible requerido
  - Oxígeno necesario
  - Tiempo estimado de viaje
- Considera factores de emergencia y reservas

#### `startSimulation()`
- Inicia la simulación del viaje espacial
- Gestiona la secuencia principal de eventos

#### `simulateSpaceHazards()`
- Simula eventos aleatorios durante el viaje
- Gestiona daños a la nave y reparaciones
- Incluye una barra de progreso visual
- Permite interacción del usuario durante eventos críticos

#### `showDetailedStats()`
- Muestra estadísticas detalladas del viaje:
  - Tasa de éxito
  - Consumo de recursos
  - Daños sufridos

## Características Técnicas
- Sistema de colores en consola para mejor visualización
- Gestión de recursos con factor de emergencia (20% extra)
- Sistema de eventos aleatorios con diferentes niveles de severidad
- Registro detallado de viajes realizados
- Barra de progreso dinámica con códigos de color

## Cómo Usar el Simulador
1. Ejecutar el programa
2. Seleccionar un planeta destino
3. Elegir una nave espacial y configurar pasajeros
4. Calcular recursos necesarios
5. Iniciar la simulación
6. Responder a eventos durante el viaje
7. Revisar estadísticas finales

## Sistema de Eventos y Daños
- 5 tipos diferentes de eventos espaciales
- Sistema de daños por componentes
- Kits de reparación disponibles
- Monitoreo en tiempo real del estado de la nave