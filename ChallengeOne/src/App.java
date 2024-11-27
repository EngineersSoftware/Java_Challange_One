import java.util.ArrayList;
import java.util.Scanner;
import java.util.Random;

public class App {

    // Definir variables globales
    static Scanner scanner = new Scanner(System.in);
    static Random random = new Random();

    // Definir constantes para códigos de color
    static final String GREEN = "\033[32m"; // Verde para barra de progreso
    static final String YELLOW = "\033[33m"; // Amarillo para barra de progreso
    static final String BLUE = "\033[34m"; // Azul para subtitulos
    static final String ORANGE = "\033[38;5;208m"; // Naranja en las recomendaciones
    static final String BOLD = "\033[1m"; // Negrita en subtitulos
    static final String RESET = "\033[0m"; // Resetea el color 
    static final String BRIGHT_BLUE = "\033[94m"; // Azul brillante Titulos
    static final String BRIGHT_GREEN = "\033[92m"; // Verde brillante Soluciones
    static final String UNDERLINE = "\033[4m"; // Subrayado en titulos

    // Información de los planetas con descripición.
    static String[] planets = {
            "Marte: Es un planeta pequeño y rocoso tiene una atmósfera delgada y enrarecida",
            "Júpiter: Planeta sin superficie sólida, y con una atmósfera de nubes rojizas",
            "Saturno: El único con un sistema de anillos visible desde la Tierra"
    };

    // milliones de kilometros desde la tierra.
    static double[] distances = { 225.0, 628.0, 1426.0 };

    // Información de la nave espacial.
    static String[] spaceships = { "Apolo 11", "Soyus", "Mercury-Redstone" }; // Nombres de las naves
    static double[] speeds = { 200.0, 150.0, 300.0 }; // km/h

    // Constantes de consumo de recursos
    static double fuelConsumptionPerMillionKm = 1000.0; // Consumo de combustible en litros por millon de kilometros
    static double oxygenConsumptionPerPersonHour = 0.5; // Consumo de oxígeno en litros por persona por hora
    static double emergencyReserveFactor = 1.2; // 20% extra para emergencias

    // Variables para la cantidad de pasajeros
    static int passengerCounts = 0;

    // Sistema de daños y reparaciones
    static String[] shipComponents = { "Motor", "Sistema de Oxígeno", "Escudo", "Sistema Eléctrico", "Casco" }; // Nombres de los componentes
    static int[] componentHealth = { 100, 100, 100, 100, 100 }; // Salud de cada componente en porcentaje
    static int[] repairKits = { 3, 3, 3 }; // Kits de reparación por nave

    // Sistema de eventos espaciales
    static String[] eventNames = {
            "Lluvia de Meteoritos",
            "Fallo del Sistema",
            "Radiación Solar",
            "Micrometeoritos",
            "Turbulencia Espacial"
    };

    // Descripción de los eventos
    static String[] eventDescriptions = {
            "Una densa lluvia de meteoritos golpea la nave",
            "Un sistema crítico presenta fallos",
            "Una llamarada solar afecta los sistemas",
            "Pequeños impactos en el casco",
            "Perturbaciones en el espacio afectan la nave"
    };

    // Severidad y daños de los eventos
    static int[] eventSeverity = { 3, 2, 2, 1, 1 }; // 1-leve, 2-moderado, 3-severo
    static int[] eventDamage = { 30, 20, 15, 10, 5 }; // Daños en porcentaje

    // Registro de viajes, guarda información de viajes realizados
    static ArrayList<String> tripDestinations = new ArrayList<>(); // Destino
    static ArrayList<String> tripSpaceships = new ArrayList<>(); // Nave
    static ArrayList<Boolean> tripSuccess = new ArrayList<>(); // Resultado
    static ArrayList<Double> tripFuelUsed = new ArrayList<>(); // Combustible
    static ArrayList<Double> tripOxygenUsed = new ArrayList<>(); // Oxígeno

    // Verificación de selección de nave y planeta
    static boolean isPlanetSelected = false; // Verifica si se ha seleccionado un planeta
    static boolean isShipSelected = false; // Verifica si se ha seleccionado una nave
    static int selectedShipIndex = -1; // Indice de nave seleccionada
    static int selectedPlanetIndex = -1; // Indice de planeta seleccionado

    public static void main(String[] args) throws Exception {
        int option;
        do {
            showMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    selectPlanet();
                    break;
                case 2:
                    selectSpaceship();
                    break;
                case 3:
                    calculateResources();
                    break;
                case 4:
                    startSimulation();
                    break;
                case 0:
                    System.out.println(BRIGHT_GREEN+"Saliendo... ¡Gracias por usar la simulación!"+RESET);
                    break;
                default:
                    System.err.println(ORANGE+"¡¡Selecciona una opción válida! (0-7)."+RESET);
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    // Menú Principal
    public static void showMenu() {
        System.out.println(BOLD+BRIGHT_BLUE+UNDERLINE+"\n\t ### Menú Principal ### "+RESET+"\n");
        System.out.println(BOLD+BLUE+"1)."+RESET+" Planetas destino");
        System.out.println(BOLD+BLUE+"2)."+RESET+" Naves espaciales");
        System.out.println(BOLD+BLUE+"3)."+RESET+" Calcular recursos necesarios");
        System.out.println(BOLD+BLUE+"4)."+RESET+" Iniciar simulación");
        System.out.println(BOLD+BLUE+"0)."+RESET+" Salir");
        System.out.print(BOLD+"Por favor, elige una opción: "+RESET);
    }

    // Selección de planeta
    public static void selectPlanet() {
        System.out.println(BOLD+BRIGHT_BLUE+UNDERLINE+"\n\tMenú Planetas y sus distancias desde la Tierra "+RESET+"\n");
        for (int i = 0; i < planets.length; i++) {
            System.out.println(BOLD+BRIGHT_BLUE+(i + 1) + "). " +RESET+ planets[i] +BLUE+BOLD+ " - Distancia: " + distances[i] + " millones de km."+RESET);
        }
        System.out.print(BOLD+"Elige el número de tu planeta destino: "+RESET);
        int selection = scanner.nextInt();

        if (selection >= 1 && selection <= planets.length) {
            selectedPlanetIndex = selection - 1;
            System.out.println(BRIGHT_GREEN+"\nHas seleccionado " + planets[selectedPlanetIndex] + "."+RESET);
            isPlanetSelected = true;
        } else {
            System.err.println(ORANGE+"Selección no válida. Por favor, intenta de nuevo."+RESET);
        }
    }

    // Selección de nave espacial
    public static void selectSpaceship() {
        if (!isPlanetSelected) {
            System.err.println(ORANGE+"Primero debes seleccionar un planeta destino."+RESET);
            return;
        }
        System.out.println(BOLD+BRIGHT_BLUE+UNDERLINE+"\n\t### Naves espaciales disponibles ###\n"+RESET);
        for (int i = 0; i < spaceships.length; i++) {
            System.out.println(BOLD+BRIGHT_BLUE+(i + 1) + "). " +RESET+ spaceships[i] +": " +BLUE+BOLD+" Velocidad: " + speeds[i] + " km/h"+RESET);
        }
        System.out.print(BOLD+"Elige el número de tu nave espacial: ");
        int selection = scanner.nextInt();

        if (selection >= 1 && selection <= spaceships.length) {
            selectedShipIndex = selection - 1;
            System.out.println(BRIGHT_GREEN+BOLD+"Has seleccionado la nave: " +RESET+ spaceships[selectedShipIndex] + ".");
            System.out.print(BOLD+"Por favor, ingresa la cantidad de pasajeros: "+RESET);
            passengerCounts = scanner.nextInt();
            while (passengerCounts <= 0) {
                System.out.println(ORANGE+"La cantidad de pasajeros debe ser mayor a 0."+RESET);
                System.out.print(BOLD+"Ingresa la cantidad de pasajeros para el viaje: "+RESET);
                passengerCounts = scanner.nextInt();
            }

            isShipSelected = true;
            resetShipComponents();
        } else {
            System.err.println(ORANGE+"Selección no válida. Por favor, intenta de nuevo."+RESET);
        }
    }

    // // Metodo para reniciar la nave espacial
    private static void resetShipComponents() {
        for (int i = 0; i < componentHealth.length; i++) {
            componentHealth[i] = 100;
        }
    }

    // Calcular recursos necesarios
    public static void calculateResources() {
        if (!isPlanetSelected || !isShipSelected) {
            System.err.println(ORANGE+"¡ERROR! Debes seleccionar un planeta y una nave antes de calcular los recursos."+RESET);
            return;
        }

        // Calcular duración del viaje en horas
        double distanceInKm = distances[selectedPlanetIndex] * 1_000_000; // Convertir a kilómetros
        double travelTime = distanceInKm / speeds[selectedShipIndex];
        double tripDuration = Math.ceil(travelTime);

        // Calcular combustible requerido (basado en la distancia)
        double baseFuel = (distances[selectedPlanetIndex]) * fuelConsumptionPerMillionKm;
        double totalFuel = baseFuel * emergencyReserveFactor;

        // Calcular oxígeno requerido (basado en la duración del viaje y la cantidad de pasajeros)
        double totalOxygen = tripDuration * passengerCounts * oxygenConsumptionPerPersonHour * emergencyReserveFactor;

        // Mostrar resultados
        System.out.println(BLUE+"\nResultados del Cálculo de Recursos Necesarios:"+RESET);
        System.out.printf(BOLD+"\nDistancia total:"+RESET+" %.2f millones de km\n", distances[selectedPlanetIndex]);
        System.out.printf(BOLD+"Duración del viaje:"+RESET+" %.2f horas (%.2f días)\n", tripDuration, tripDuration / 24);
        System.out.printf(BOLD+"Cantidad de pasajeros:"+RESET+" %d\n", passengerCounts);
        System.out.printf(BOLD+"Combustible requerido:"+RESET+" %.2f litros\n", totalFuel);
        System.out.printf(BOLD+"Oxígeno requerido:"+RESET+" %.2f litros\n", totalOxygen);
        System.out.println(GREEN+"---- Recursos calculados exitosamente ----"+RESET);
    }

    // Iniciar la simulación
    public static void startSimulation() {
        if (!isPlanetSelected || !isShipSelected) {
            System.err.println(ORANGE+"¡ERROR! Debes seleccionar un planeta y una nave antes de iniciar el viaje."+RESET);
            return;
        }

        System.out.println(GREEN+"\nIniciando simulación de viaje..."+RESET);
        System.out.printf(BLUE+"Nave:"+RESET+" %s\n", spaceships[selectedShipIndex]);
        System.out.printf(BLUE+"Destino:"+RESET+" %s\n", planets[selectedPlanetIndex]);
        simulateSpaceHazards();
    }

    // Simulación de eventos espaciales
    private static void simulateSpaceHazards() {
        System.out.println(GREEN+"\n---- ¡Iniciando viaje espacial! ----");
        System.out.println("Preparando sistemas de la nave...\n"+RESET);

        boolean catastrophicFailure = false;
        int numberOfEvents = 2 + random.nextInt(3); // 2-4 eventos por viaje
        int[] eventPoints = new int[numberOfEvents];

        // Determinar puntos donde ocurrirán los eventos (entre 40 y 80 del progreso)
        for (int i = 0; i < numberOfEvents; i++) {
            eventPoints[i] = 40 + random.nextInt(61); // Números entre 40 y 80
        }

        // Ordenar los puntos de manera manual (usando bubble sort)
        for (int i = 0; i < eventPoints.length - 1; i++) {
            for (int j = 0; j < eventPoints.length - i - 1; j++) {
                if (eventPoints[j] > eventPoints[j + 1]) {
                    int temp = eventPoints[j];
                    eventPoints[j] = eventPoints[j + 1];
                    eventPoints[j + 1] = temp;
                }
            }
        }

        // Simulación de preparación con barra de progreso
        int currentEvent = 0;
        for (int i = 0; i <= 100 && !catastrophicFailure; i += 2) {
            showProgressBar(i);

            // Verificar si hay un evento en este punto
            if (currentEvent < numberOfEvents && i >= eventPoints[currentEvent]) {
                System.out.println(ORANGE+"\n\n---- ¡EVENTO DETECTADO! ----"+RESET);

                // Generar daño
                int componentIndex = random.nextInt(shipComponents.length);
                int eventIndex = random.nextInt(eventNames.length);

                System.out.printf(ORANGE+"\n¡%s!\n"+RESET, eventNames[eventIndex]);
                System.out.println(eventDescriptions[eventIndex]);

                // Aplicar daño
                componentHealth[componentIndex] = Math.max(0,componentHealth[componentIndex] - eventDamage[eventIndex]);
                System.out.printf(ORANGE+"¡%s dañado! "+RESET+BOLD+" Salud actual: %d%%"+RESET+"\n", shipComponents[componentIndex],componentHealth[componentIndex]);

                if (componentHealth[componentIndex] < 30) {
                    System.out.println(ORANGE+"¡ADVERTENCIA! " + shipComponents[componentIndex]+ "en estado crítico. Se recomienda reparación inmediata."+RESET);
                }

                // Verificar si el componente está completamente dañado
                if (componentHealth[componentIndex] == 0) {
                    System.out.printf(ORANGE+"¡¡¡%s completamente destruido!!!\n", shipComponents[componentIndex]);
                    System.out.println("¡¡¡MISIÓN ABORTADA!!!"+RESET);
                    catastrophicFailure = true;
                } else {
                    // Preguntar por reparación
                    System.out.println(BLUE+"\n¿Deseas usar un kit de reparación?"+RESET+BOLD+" (1: Sí, 0: No)"+RESET);
                    if (scanner.nextInt() == 1 && repairKits[selectedShipIndex] > 0) {
                        int repairAmount = 50;
                        componentHealth[componentIndex] = Math.min(100, componentHealth[componentIndex] + repairAmount);repairKits[selectedShipIndex]--;
                        System.out.printf(GREEN+"%s reparado. Salud actual:"+RESET+" %d%%. Kits restantes: %d\n",shipComponents[componentIndex], componentHealth[componentIndex],repairKits[selectedShipIndex]);
                    }
                }
                currentEvent++;
                System.out.println(BOLD+"\nPresiona ENTER para continuar el viaje..."+RESET);
                scanner.nextLine(); // Limpiar el buffer
                scanner.nextLine(); // Esperar ENTER
            }
            // Pequeña pausa
            for (long x = 0; x < 10000000; x++) {
            }
        }

        // Registrar el resultado del viaje
        double fuelUsed = distances[selectedPlanetIndex] * fuelConsumptionPerMillionKm;
        double travelTime = (distances[selectedPlanetIndex] * 1_000_000) / speeds[selectedShipIndex];
        double oxygenUsed = travelTime * passengerCounts * oxygenConsumptionPerPersonHour;

        recordTrip(!catastrophicFailure, fuelUsed, oxygenUsed);

        if (!catastrophicFailure) {
            System.out.println(BRIGHT_GREEN+"\n¡Viaje completado con éxito!"+RESET);
        }

        // Mostrar estadísticas del viaje
        showDetailedStats();
    }

    //Mostrar estadísticas del viaje
    private static void showDetailedStats() {
        if (tripDestinations.isEmpty()) {
            System.out.println(ORANGE + "\nNo hay estadísticas disponibles." + RESET);
            return;
        }

        int totalTrips = tripDestinations.size();
        int successfulTrips = 0;
        double totalFuel = 0;
        double totalOxygen = 0;
        int totalDamage = 0;

        for (int i = 0; i < totalTrips; i++) {
            if (tripSuccess.get(i))
                successfulTrips++;
            totalFuel += tripFuelUsed.get(i);
            totalOxygen += tripOxygenUsed.get(i);
        }

        System.out.println(BRIGHT_BLUE+"\n\t ---- ESTADÍSTICAS GLOBALES ----"+RESET);
        System.out.printf(BOLD+BLUE+"Total de viajes:" + RESET + " %d\n", totalTrips);
        System.out.printf(BOLD+BLUE+"Tasa de éxito:"+RESET+ " %.1f%%\n", (successfulTrips * 100.0) / totalTrips);
        System.out.printf(BOLD+BLUE+"Promedio de combustible:"+RESET+" %.2f L\n", totalFuel / totalTrips);
        System.out.printf(BOLD+BLUE+"Promedio de oxígeno:"+RESET+" %.2f L\n", totalOxygen / totalTrips);
        System.out.printf(BOLD+BLUE+"Promedio de daños:"+RESET+" %.1f%%\n", totalDamage * 1.0 / totalTrips);
    }

    // Mostrar barra de progreso
    private static void showProgressBar(int percentage) {
        System.out.print(BOLD+"\r[");
        int progress = percentage / 2;
        for (int i = 0; i < 50; i++) {
            if (i < progress) {
                if (i < 20) {
                    System.out.print("-"+GREEN); // Verde para inicio
                } else if (i < 40) {
                    System.out.print("-"+ORANGE); // Naranjado para medio
                } else {
                    System.out.print("-"+GREEN); // Verder para final
                }
            } else {
                System.out.print(" ");
            }
        }
        System.out.print(RESET+"] " + percentage + "% ");

        // Añadir mensaje de estado
        if (percentage < 30) {
            System.out.print("Iniciando sistemas...");
        } else if (percentage < 60) {
            System.out.print("Verificando componentes...");
        } else if (percentage < 90) {
            System.out.print("Preparando para despegue...");
        } else {
            System.out.print("¡Sistemas listos!");
        }

        if (percentage == 100)
            System.out.println();
    }

    //Registrar el resultado del viaje espacial
    private static void recordTrip(boolean success, double fuelUsed, double oxygenUsed) {
        tripDestinations.add(planets[selectedPlanetIndex].split(":")[0]);
        tripSpaceships.add(spaceships[selectedShipIndex]);
        tripSuccess.add(success);
        tripFuelUsed.add(fuelUsed);
        tripOxygenUsed.add(oxygenUsed);
    }

}
