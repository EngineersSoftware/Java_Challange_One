import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class App {

    static Scanner scanner = new Scanner(System.in);
   //Sistema de daños y reparaciones
    static String[] shipComponents = { "Motor", "Sistema de Oxígeno", "Escudo", "Sistema Eléctrico", "Casco" }; //Nombres de los componentes
    static int[] componentHealth = { 100, 100, 100, 100, 100 }; //Salud de cada componente en porcentaje
    static int[] repairKits = { 3, 3, 3 };// Kits de reparación por nave

    //Sistema de eventos espaciales
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

    //Severidad y daños de los eventos
    static int[] eventSeverity = { 3, 2, 2, 1, 1 }; //1-leve, 2-moderado, 3-severo
    static int[] eventDamage = { 30, 20, 15, 10, 5 }; //Daños en porcentaje

    //Registro de viajes, guarda información de viajes realizados
    static ArrayList<String> tripDestinations = new ArrayList<>();// Destino
    static ArrayList<String> tripSpaceships = new ArrayList<>(); //Nave
    static ArrayList<Boolean> tripSuccess = new ArrayList<>(); //Resultado
    static ArrayList<Double> tripFuelUsed = new ArrayList<>(); //Combustible
    static ArrayList<Double> tripOxygenUsed = new ArrayList<>();// Oxígeno

    // Definir constantes para códigos de color
    static final String GREEN = "\033[32m"; // Verde para barra de progreso
    static final String YELLOW = "\033[33m"; // Amarillo para barra de progreso
    static final String BLUE = "\033[34m"; // Azul para subtitulos
    static final String ORANGE = "\033[38;5;208m"; // Naranja en las recomendaciones
    static final String BOLD = "\033[1m"; // Negrita en subtitulos
    static final String RESET = "\033[0m"; // Resetea el color 
    static final String BRIGHT_BLUE = "\033[94m"; // Azul brillante Titulos
    static final String BRIGHT_GREEN = "\033[92m"; // Verde brillante Soluciones
    static final String UNDERLINE = "\033[4m"; // Subrayado en titulos
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
                    selectShip();
                    break;
                case 3:
                    iniciarSimulacion();
                    break;
                case 4:
                    System.out.println("\033[32mSaliendo... ¡Gracias por usar la simulación!\033[0m");
                    break;
                default:
                    System.err.println("\033[31m¡¡Selecciona una opción válida! (1-4).\033[0m");
                    break;
            }
        } while (option != 4);
    }

     //Menú Principal
    public static void showMenu() {
        System.out.println("\n\033[1;4;32m\t ### Menú Principal ### \033[0m\n");
        System.out.println("\033[1m1).\033[0m Planeta destino");
        System.out.println("\033[1m2).\033[0m Nave espacial");
        System.out.println("\033[1m3).\033[0m Iniciar la simulación del vuelo");
        System.out.println("\033[1m4).\033[0m Salir");
        System.out.print("\033[1mPor favor, elige una opción: \033[0m");
    }

     //Selección de planeta
    public static void selectPlanet() {
        System.out.println("\n\033[4;1m\tMenú Planetas y sus distancias desde la Tierra \033[0m\n");
        for (int i = 0; i < planets.length; i++) {
            System.out.println((i + 1) + ". " + planets[i] + " - Distancia: " + distance[i] + " millones de km");
        }
        System.out.print("\033[1mElige el número de tu planeta destino: ");
        int selection = scanner.nextInt();

        if (selection >= 1 && selection <= planets.length) {
            System.out.println("\n\033[32mHas seleccionado " + planets[selection - 1] + ".\033[0m");
            selectPlanet = true;  //Se marca como seleccionado el planeta
        } else {
            System.err.println("\033[31mSelección no válida. Por favor, intenta de nuevo.\033[0m");
        }
    }

     //Selección de nave
    public static void selectShip() {
        System.out.println("\n\033[1;34mSelecciona una nave espacial:\033[0m");
        for (int i = 0; i < ships.length; i++) {
            System.out.println((i + 1) + ". " + ships[i] + " - Velocidad: " + speeds[i] + " km/h");
        }
        System.out.print("\033[1mElige el número de tu nave espacial: ");
        int option = scanner.nextInt();

        if (option >= 1 && option <= ships.length) {
            System.out.println("\033[32mHas seleccionado la nave " + ships[option - 1] + ".\033[0m");
            selectShip = true; // Se marca como seleccionada la nave
        } else {
            System.err.println("\033[31mSelección no válida. Por favor, intenta de nuevo.\033[0m");
        }

    }

    // Iniciar la simulación
    public static void iniciarSimulacion() {
        if (selectPlanet && selectShip) {
            System.out.println("\n\033[1;34mIniciando simulación de viaje...\033[0m");

        } else {
            System.err.println("\033[31m¡ERROR! Debes seleccionar un planeta y una nave antes de iniciar el viaje.\033[0m");
        }
    }

    // Simulación de eventos espaciales
     private static void simulateSpaceHazards() {
         System.out.println(GREEN+"\n---- ¡Iniciando viaje espacial! ----");
         System.out.println("Preparando sistemas de la nave...\n"+RESET);

         boolean catastrophicFailure = false;
         int numberOfEvents = 2 + random.nextInt(3);//  2-4 eventos por viaje
         int[] eventPoints = new int[numberOfEvents];

         // Determinar puntos donde ocurrirán los eventos (entre 40 y 80 del progreso)
         for (int i = 0; i < numberOfEvents; i++) {
             eventPoints[i] = 40 + random.nextInt(61);  //Números entre 40 y 80
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

        //  Simulación de preparación con barra de progreso
         int currentEvent = 0;
         for (int i = 0; i <= 100 && !catastrophicFailure; i += 2) {
             showProgressBar(i);

            //  Verificar si hay un evento en este punto
             if (currentEvent < numberOfEvents && i >= eventPoints[currentEvent]) {
                 System.out.println(ORANGE+"\n\n---- ¡EVENTO DETECTADO! ----"+RESET);

                 // Generar daño
                 int componentIndex = random.nextInt(shipComponents.length);
                 int eventIndex = random.nextInt(eventNames.length);

                 System.out.printf(ORANGE+"\n¡%s!\n"+RESET, eventNames[eventIndex]);
                 System.out.println(eventDescriptions[eventIndex]);

                  //Aplicar daño
                 componentHealth[componentIndex] = Math.max(0,componentHealth[componentIndex] - eventDamage[eventIndex]);
                 System.out.printf(ORANGE+"¡%s dañado! "+RESET+BOLD+" Salud actual: %d%%"+RESET+"\n", shipComponents[componentIndex],componentHealth[componentIndex]);

                 if (componentHealth[componentIndex] < 30) {
                     System.out.println(ORANGE+"¡ADVERTENCIA! " + shipComponents[componentIndex]+ "en estado crítico. Se recomienda reparación inmediata."+RESET);
                 }

                  //Verificar si el componente está completamente dañado
                 if (componentHealth[componentIndex] == 0) {
                     System.out.printf(ORANGE+"¡¡¡%s completamente destruido!!!\n", shipComponents[componentIndex]);
                     System.out.println("¡¡¡MISIÓN ABORTADA!!!"+RESET);
                     catastrophicFailure = true;
                 } else {
                    //  Preguntar por reparación
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
                 scanner.nextLine();  //Esperar ENTER
             }
              //Pequeña pausa
             for (long x = 0; x < 10000000; x++) {
             }
         }

          //Registrar el resultado del viaje
         double fuelUsed = distances[selectedPlanetIndex] * fuelConsumptionPerMillionKm;
         double travelTime = (distances[selectedPlanetIndex] * 1_000_000) / speeds[selectedShipIndex];
         double oxygenUsed = travelTime * passengerCounts * oxygenConsumptionPerPersonHour;

         recordTrip(!catastrophicFailure, fuelUsed, oxygenUsed);

         if (!catastrophicFailure) {
             System.out.println(BRIGHT_GREEN+"\n¡Viaje completado con éxito!"+RESET);
         }

          //Mostrar estadísticas del viaje
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

      //Mostrar barra de progreso
     private static void showProgressBar(int percentage) {
         System.out.print(BOLD+"\r[");
         int progress = percentage / 2;
         for (int i = 0; i < 50; i++) {
             if (i < progress) {
                 if (i < 20) {
                     System.out.print("-"+GREEN); // Verde para inicio
                 } else if (i < 40) {
                     System.out.print("-"+ORANGE);  //Naranjado para medio
                 } else {
                     System.out.print("-"+GREEN);  //Verder para final
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


    // Metodo para reniciar la nave espacial
     private static void resetShipComponents() {
         for (int i = 0; i < componentHealth.length; i++) {
             componentHealth[i] = 100;
         }
         }

    
    public static void detenerNave() {

    }

}
