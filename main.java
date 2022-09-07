import java.util.Scanner;

/**
 * Exam
 * 
 * 1. Print a menu
 * 2. Wait for user input
 * 3. Call the methods in the menu
 * 
 * See the method descriptions for more
 * 
 * @author Fredrik von Post, efiovo-1
 */

public class Main {

    private static Scanner userInput = new Scanner(System.in);

    public static void main(String[] args) {

        int selection = 0; // used in the switch/case block to call the methods

        // contains the ID and asking price, the array index is the reference to
        // objectsAddressType
        int[][] objectsIDprice = new int[100][2];
        // contains the object address and object type, the array index is the reference
        // to objectsIDprice
        String[][] objectsAddressType = new String[100][2];

        // contains object IDs and bids, the array index is the reference to bidsBidder
        // and bidsAccepted
        int[][] bidsIDprice = new int[100][2];
        // contains names of bidders, the array index is the reference to bidsIDprice
        // and bidsAccepted
        String[] bidsBidder = new String[100];

        // contains accepted bids, the array index is the reference to bidsIDprice and
        // bidsBidder

        Boolean[] bidsAccepted = new Boolean[100];

        for (int i = 0; i < bidsAccepted.length; i++) {
            bidsAccepted[i] = false;
        }

        // main user input loop
        while (selection != 7) {
            // prints the menu
            selection = menu();
            switch (selection) {
                case 1: // Register new object
                    objectsAddressType = registerAddressType(objectsAddressType);
                    objectsIDprice = registerObjectIDprice(objectsIDprice);
                    break;
                case 2: // Register bid
                    bidsIDprice = registerBid(bidsIDprice, objectsIDprice);
                    bidsBidder = registerBid(bidsBidder);
                    int i = 0;
                    for (int[] row : bidsIDprice) {
                        if (row[0] != 0) {
                            System.out.printf("%d %d %s%n", row[0], row[1], bidsBidder[i]);
                            i++;
                        }

                    }
                    break;
                case 3: // End bidding process
                    bidsAccepted = endBidding(bidsAccepted, bidsBidder, bidsIDprice);
                    break;
                case 4: // Print bidding history for object
                    printBiddingHistory(bidsIDprice, bidsBidder, bidsAccepted);
                    break;
                case 5: // Print all unsold objects
                    printUnsold(bidsAccepted, bidsIDprice, objectsIDprice, objectsAddressType);

                    break;
                case 6: // Print all unsold objects (by price)
                    break;
                case 7: // End program
                    break;

                default:
                    System.out.println("Enter a valid option.");
            }
        }
    }

    public static void printUnsold(Boolean[] bidsAccepted, int[][] bidsIDprice, int[][] objectsIDprice,
            String[][] objectsAddressType) {

        System.out.println("ID Address Type Asking Price Sold for");
        for (int i = 0; i < bidsAccepted.length; i++) {
            if (!bidsAccepted[i]) {
                System.out.printf("%d %s %s %d %d%n", bidsIDprice[i][0], objectsAddressType[i][0],
                        objectsAddressType[i][1], objectsIDprice[i][1], bidsIDprice[i][1]);
            }

        }
    }

    public static void printBiddingHistory(int[][] bidsIDprice, String[] bidsBidder, Boolean[] bidsAccepted) {
        int id = 0;

        System.out.print("Enter object ID: ");
        id = userInput.nextInt();

        System.out.println("Object Bidder Price Accepted");

        for (int j = 0; j < bidsIDprice.length; j++) {
            if (bidsIDprice[j][0] == id) {
                System.out.printf("%d %s %d %s%n", bidsIDprice[j][0], bidsBidder[j], bidsIDprice[j][1],
                        bidsAccepted[j]);
            }
        }
    }

    public static Boolean[] endBidding(Boolean[] bidsAccepted, String[] bidsBidder, int[][] bidsIDprice) {

        int id;
        String name = null;
        int highestBid = 0;
        int i = 0;

        System.out.println("Enter object's id number: ");
        id = userInput.nextInt();

        for (int[] row : bidsIDprice) {
            if (row[0] == id) {
                highestBid = row[1];
                name = bidsBidder[i];
            }
        }
        System.out.printf("Accept bid by %s (%d)? ", name, highestBid);
        bidsAccepted[i] = true;
        return bidsAccepted;
    }

    public static String[] registerBid(String[] bidsBidder) {
        String name;
        userInput.nextLine();

        System.out.print("Enter bidders name: ");
        name = userInput.nextLine();

        for (int i = 0; i < bidsBidder.length; i++) {
            if (bidsBidder[i] == null) {
                bidsBidder[i] = name;
                break;
            }
        }

        for (String string : bidsBidder) {
            if (string != null) {
                System.out.println(string);
            }
        }
        return bidsBidder;
    }

    public static int[][] registerBid(int[][] bidsIDprice, int[][] objectsIDprice) {
        int id = 0;
        int bid = 0;
        boolean foundID = false;

        while (!foundID) {
            System.out.println("Enter object's ID number: ");
            id = userInput.nextInt();
            for (int[] is : objectsIDprice) {
                if (is[0] == id) {
                    foundID = true;
                    break;
                } else {
                    System.out.println("Could not find object");
                }
            }
        }
        System.out.println("Enter bid: ");
        bid = userInput.nextInt();

        for (int[] row : bidsIDprice) {
            if (row[0] == 0) {
                row[0] = id;
                row[1] = bid;
                break;
            }
        }
        return bidsIDprice;
    }


    public static String[][] registerAddressType(String[][] objectsAddressType) {
        String address;
        String type;

        userInput.nextLine(); // clears the scanner
        System.out.println("Enter property's address: ");
        address = userInput.nextLine();
        System.out.println("Enter property's type (Apartment, House or Commercial:) ");
        type = userInput.nextLine();

        for (String[] is : objectsAddressType) {
            if (is[0] == null) {
                is[0] = address;
                is[1] = type;
                break;
            }
        }
        return objectsAddressType;
    }

    public static String inputString() {
        String output;
        output = userInput.nextLine();
        return output;
    }

    public static int[][] registerObjectIDprice(int[][] objectsIDprice) {
        int price = 0;
        int id = randomInt(1000, 9999);
        boolean needID = false;

        do {
            id = randomInt(1000, 9999);
            for (int[] is : objectsIDprice) {
                if (is[0] == id) {
                    needID = true;
                    break;
                }
            }
        } while (needID);

        System.out.println("Enter starting price: ");
        while (true) {
            try {
                price = userInput.nextInt();
                break;
            } catch (Exception e) {
                System.out.println("Enter a valid price (positive integer).");
                userInput.nextLine();
            }
        }
        for (int[] is : objectsIDprice) {
            if (is[0] == 0) {
                is[0] = id;
                is[1] = price;
                System.out.printf("Object ID: %d%n", id);
                break;
            }
        }
        return objectsIDprice;
    } // end method registerObjectIDPrice

    /**
     * Prints a menu. Prompts for user input.
     * 
     * @return int in the range
     */
    public static int menu() {
        String selection = null;
        int output;
        // print menu
        System.out.println();
        System.out.println("1. Register new object");
        System.out.println("2. Register bid");
        System.out.println("3. End bidding process");
        System.out.println("4. Print bidding history for object");
        System.out.println("5. Print all unsold objects");
        System.out.println("6. Print all sold objects (by price)");
        System.out.println("q. End program");
        System.out.print("Enter your option: ");
        // wait for input

        while (true) {
            try {
                selection = userInput.next();
                if (Integer.valueOf(selection) >= 1 || Integer.valueOf(selection) <= 6) {
                    output = Integer.valueOf(selection);
                    break;
                } else {
                    System.out.println("Enter a valid input");
                }
            } catch (Exception e) {
                if (selection.equals("q")) {
                    output = 7;
                    break;
                } else {
                    System.out.println("Enter a valid input");
                }
            }
        }
        return output;
    } // end method menu

    /**
     * Returns a random in the specified range. Uses Math.random.
     * 
     * @param floor int
     * @param top   int
     * @return a random int in the inclusive range [floor...top]
     */
    public static int randomInt(int floor, int top) {
        int random;
        random = floor + (int) (Math.random() * ((top - floor) + 1));
        return random;
    } // end method randomInt

    /**
     * Sorts the inMatrix int[][] in ascending order by int[column]. Uses the bubble
     * sort algorithm.
     * 
     * @param inMatrix
     * @param column
     * @return
     */
    public static int[][] sortMatrix(int[][] inMatrix, int column) {
        int inMatrixLength = inMatrix.length;
        int inMatrixWidth = inMatrix[0].length;
        // sort the 2d array with bubble sort
        for (int i = 0; i < inMatrixLength; i++) {
            for (int j = 1; j < (inMatrixLength - i); j++) {
                if (inMatrix[j - 1][column] > inMatrix[j][column]) { // compare the elements in column
                    // swap the elements
                    int[] temp = new int[inMatrixWidth];
                    temp = inMatrix[j - 1];
                    inMatrix[j - 1] = inMatrix[j];
                    inMatrix[j] = temp;
                }
            }
        }
        return inMatrix;
    } // end method sortMatrix

    /**
     * Prints all rows of articles int[][] by ascending order of int[0].
     * 
     * @param articles
     */
    public static void printMatrix(int[][] articles) {
        System.out.println("ObjectID Asking price");
        // print rows where the article number is non-zero
        for (int[] is : articles) {
            if (is[0] != 0) {
                System.out.printf("%-13d %-5d", is[0], is[1]);
            }
        }
    } // end method

    /**
     * Prints all rows of array String[][].
     * 
     * @param articles
     */
    public static void printMatrix(String[][] matrix) {
        System.out.println();
        System.out.println("Address Type");
        // print rows where the article number is non-zero
        for (String[] is : matrix) {
            if (is[0] != null) {
                System.out.printf("%-13s %-5s%n", is[0], is[1]);
            }
        }
    } // end method
}
