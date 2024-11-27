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
        } else {
            System.err.println(ORANGE+"Selección no válida. Por favor, intenta de nuevo."+RESET);
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
    }



}