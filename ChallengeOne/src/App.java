import java.util.Scanner;

public class App {
    static Scanner scanner = new Scanner(System.in);

    // Información de los planetas con descripición.
    static String[] planets = {
            "Marte: Es un planeta pequeño y rocoso tiene una atmósfera delgada y enrarecida",
            "Júpiter: Planeta sin superficie sólida, y con una atmósfera de nubes rojizas",
            "Saturno: El único con un sistema de anillos visible desde la Tierra"
    };
    static double[] distances = { 225.0, 628.0, 1426.0 }; // milliones de kilometros desde la tierra.

    // Información de la nave espacial.
    static String[] spaceships = { "Exploradora", "Carga pesada", "Velocidad máxima" };
    static int[] crewCapacity = { 5, 10, 15 };
    static double[] speeds = { 20_000.0, 15_000.0, 30_000.0 }; // km/h

    // Peligros espaciales
    static String[] spaceHazards = { "Meteoritos", "Asteroides", "Turbulencias" };

    // Constantes de consumo de recursos
    static double fuelConsumptionPerMillionKm = 1000.0; // Litros.
    static double oxygenConsumptionPerPersonHour = 0.5; // Litros.
    static double emergencyReserveFactor = 1.2; // 20% extra para emergencias

    // Capacidad de combustible y oxígeno de las naves
    static double[] fuelCapacity = { 5000.0, 75000.0, 100000.0 }; // Litros
    static double[] oxygenCapacity = { 1000.0, 2000.0, 3000.0 }; // Litros

    // Verificación de selección
    static boolean isPlanetSelected = false;
    static boolean isShipSelected = false;
    static int selectedShipIndex = -1;
    static int selectedPlanetIndex = -1;

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
                    System.out.println("\033[32mSaliendo... ¡Gracias por usar la simulación!\033[0m");
                    break;
                default:
                    System.err.println("\033[31m¡¡Selecciona una opción válida! (0-4).\033[0m");
                    break;
            }
        } while (option != 0);
        scanner.close();
    }

    // Menú Principal
    public static void showMenu() {
        System.out.println("\n\033[1;4;32m\t ### Menú Principal ### \033[0m\n");
        System.out.println("\033[1m1).\033[0m Planeta destino");
        System.out.println("\033[1m2).\033[0m Nave espacial");
        System.out.println("\033[1m3).\033[0m Calcular recursos necesarios");
        System.out.println("\033[1m4).\033[0m Iniciar simulación");
        System.out.println("\033[1m0).\033[0m Salir");
        System.out.print("\033[1mPor favor, elige una opción: \033[0m");
    }

    // Selección de planeta
    public static void selectPlanet() {
        System.out.println("\n\033[4;1m\tMenú Planetas y sus distancias desde la Tierra \033[0m\n");
        for (int i = 0; i < planets.length; i++) {
            System.out.println((i + 1) + ". " + planets[i] + " - Distancia: " + distances[i] + " millones de km");
        }
        System.out.print("\033[1mElige el número de tu planeta destino: ");
        int selection = scanner.nextInt();

        if (selection >= 1 && selection <= planets.length) {
            selectedPlanetIndex = selection - 1;
            System.out.println("\n\033[32mHas seleccionado " + planets[selectedPlanetIndex] + ".\033[0m");
            isPlanetSelected = true;
        } else {
            System.err.println("\033[31mSelección no válida. Por favor, intenta de nuevo.\033[0m");
        }
    }

    // Selección de nave
    public static void selectSpaceship() {
        System.out.println("\n\033[1;34mSelecciona una nave espacial:\033[0m");
        for (int i = 0; i < spaceships.length; i++) {
            System.out.println((i + 1) + ". " + spaceships[i] + " - Velocidad: " + speeds[i] + " km/h");
        }
        System.out.print("\033[1mElige el número de tu nave espacial: ");
        int selection = scanner.nextInt();

        if (selection >= 1 && selection <= spaceships.length) {
            selectedShipIndex = selection - 1;
            System.out.println("\033[32mHas seleccionado la nave " + spaceships[selectedShipIndex] + ".\033[0m");
            isShipSelected = true;
        } else {
            System.err.println("\033[31mSelección no válida. Por favor, intenta de nuevo.\033[0m");
        }
    }

    public static void calculateResources() {
        if (!isPlanetSelected || !isShipSelected) {
            System.err.println(
                    "\033[31m¡ERROR! Debes seleccionar un planeta y una nave antes de calcular los recursos.\033[0m");
            return;
        }

        // Calcular duración del viaje requerido
        double distance = distances[selectedPlanetIndex] * 1_000_000; // Convert to km
        double speed = speeds[selectedShipIndex];
        double tripDuration = distance / speed; // hours

        // Calcular combustible requerido
        double baseFuel = (distance / 1_000_000) * fuelConsumptionPerMillionKm;
        double totalFuel = baseFuel * emergencyReserveFactor;

        // Calcular oxígeno requerido
        int crew = crewCapacity[selectedShipIndex];
        double totalOxygen = tripDuration * crew * oxygenConsumptionPerPersonHour * emergencyReserveFactor;

        // Mostrar resultados
        System.out.println("\n\033[1;34m=== Resultados del Cálculo de Recursos ===\033[0m");
        System.out.printf("\nDuración del viaje: %.2f horas (%.2f días)\n", tripDuration, tripDuration / 24);
        System.out.printf("Combustible requerido: %.2f litros (Capacidad: %.2f litros)\n", totalFuel,
                fuelCapacity[selectedShipIndex]);
        System.out.printf("Oxígeno requerido: %.2f litros (Capacidad: %.2f litros)\n", totalOxygen,
                oxygenCapacity[selectedShipIndex]);

        // Verificar si los recursos son suficientes
        checkResourceSufficiency(totalFuel, totalOxygen);
    }

    private static void checkResourceSufficiency(double requiredFuel, double requiredOxygen) {
        boolean isFuelSufficient = requiredFuel <= fuelCapacity[selectedShipIndex];
        boolean isOxygenSufficient = requiredOxygen <= oxygenCapacity[selectedShipIndex];

        if (!isFuelSufficient || !isOxygenSufficient) {
            System.out.println("\n\033[31m=== ¡ADVERTENCIA: Recursos Insuficientes! ===\033[0m");
            if (!isFuelSufficient) {
                System.out.println("- ¡No hay suficiente capacidad de combustible para este viaje!");
            }
            if (!isOxygenSufficient) {
                System.out.println("- ¡No hay suficiente capacidad de oxígeno para este viaje!");
            }
            System.out.println("\nRecomendaciones:");
            System.out.println("1. Elige una nave diferente con mayor capacidad");
            System.out.println("2. Reduce el tamaño de la tripulación si es posible");
            System.out.println("3. Considera un destino más cercano");
        } else {
            System.out.println("\n\033[32m=== ¡Todos los recursos son suficientes para el viaje! ===\033[0m");
        }
    }

    // Iniciar la simulación
    public static void startSimulation() {
        if (!isPlanetSelected || !isShipSelected) {
            System.err.println(
                    "\033[31m¡ERROR! Debes seleccionar un planeta y una nave antes de iniciar el viaje.\033[0m");
            return;
        }

        if (!checkResourcesBeforeStart()) {
            System.err.println(
                    "\033[31m¡ERROR! Debes calcular y verificar los recursos antes de iniciar el viaje.\033[0m");
            return;
        }

        System.out.println("\n\033[1;34mIniciando simulación de viaje...\033[0m");
        System.out.printf("Nave: %s\n", spaceships[selectedShipIndex]);
        System.out.printf("Destino: %s\n", planets[selectedPlanetIndex]);
        simulateSpaceHazards();
    }

    private static boolean checkResourcesBeforeStart() {
        // Calcular recursos nuevamente para verificar
        double distance = distances[selectedPlanetIndex] * 1_000_000;
        double tripDuration = distance / speeds[selectedShipIndex];
        double totalFuel = (distance / 1_000_000) * fuelConsumptionPerMillionKm * emergencyReserveFactor;
        double totalOxygen = tripDuration * crewCapacity[selectedShipIndex] * oxygenConsumptionPerPersonHour
                * emergencyReserveFactor;

        return totalFuel <= fuelCapacity[selectedShipIndex] && totalOxygen <= oxygenCapacity[selectedShipIndex];
    }

    private static void simulateSpaceHazards() {
        System.out.println("\n\033[1;33m=== ¡Alerta de Peligros Espaciales! ===\033[0m");
        int hazardIndex = (int) (Math.random() * spaceHazards.length);
        System.out.printf("Se ha detectado: %s\n", spaceHazards[hazardIndex]);
        System.out.println("Iniciando maniobras evasivas...");

        // Simulación simple de resultado
        if (Math.random() > 0.3) {
            System.out.println("\033[32m¡Maniobra evasiva exitosa!\033[0m");
        } else {
            System.out.println("\033[31m¡Daños menores en la nave! Continuando el viaje...\033[0m");
        }
    }

    // // Iniciar la simulación
    // public static void iniciarSimulacion() {
    //     if (selectPlanet && selectShip) {
    //         System.out.println("\n\033[1;34mIniciando simulación de viaje...\033[0m");
    //         // Aquí puedes agregar más detalles del viaje, como la duración
    //     } else {
    //         System.err.println(
    //                 "\033[31m¡ERROR! Debes seleccionar un planeta y una nave antes de iniciar el viaje.\033[0m");
    //     }
    // }

    // Métodos auxiliares.
    public static void imprimirPlanetas() {

    }

    public static void lanzarEvento() {

    }

    public static void detenerNave() {

    }

}
