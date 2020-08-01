package io.pravega.sharktank.flink.cozmo;

public class StringMatches {

    public static void main(String[] args) {

        //String input = "Location:15,10";
        String input = "Image:dog-123.jpg";
        String[] cmdArray = input.split(":");
        if (cmdArray.length == 2) {
            if (cmdArray[0].equals("Location")) {
                String[] coordinates = cmdArray[1].split(",");
                System.out.println("x: " + Integer.parseInt(coordinates[0]));
                System.out.println("y: " + Integer.parseInt(coordinates[1]));
            } else if (cmdArray[0].equals("Image")) {
                String imageFileName = cmdArray[1];
                System.out.println("Image file name: " + imageFileName);
            }
        }
    }

}