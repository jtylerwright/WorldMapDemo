package demo.worldmap;

import com.opencsv.CSVReader;

import org.jzy3d.colors.Color;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.LineStrip;
import org.jzy3d.plot3d.primitives.Point;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WorldMap {

    public List<LineStrip> worldMapLineStrips = new ArrayList<LineStrip>();
    String newline = System.getProperty("line.separator");

    public void parseFile() {
        try {

            // Get world map csv location
            String worldMapClassPath = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
            File worldMapFile = new File (worldMapClassPath.concat("world_map.csv"));

            // Create file reader and CSV reader from world map file
            FileReader fileReader = new FileReader(worldMapFile);
            CSVReader reader = new CSVReader(fileReader);

            // Create row holder and line number counter
            String[] rowHolder;
            int lineNumber = 1;

            // Create local line strip and set color
            LineStrip lineStrip = new LineStrip();
            lineStrip.setWireframeColor(Color.BLACK);

            // Loop through rows while a next row exists
            while ((rowHolder = reader.readNext()) != null) {

                switch (rowHolder.length) {
                    case 1:
                        if (rowHolder[0].equals("")) {

                            // If row is blank, add line strip to list of line strips and clear line strip
                            worldMapLineStrips.add(lineStrip);
                            lineStrip = new LineStrip();
                            lineStrip.setWireframeColor(Color.BLACK);
                            break;
                        } else {

                            // Throw error if a map point only has one coordinate
                            String oneCoordinateError = "Error on line: " + lineNumber + newline +
                                    "The row contains only 1 coordinate";
                            JOptionPane.showMessageDialog(null, oneCoordinateError, "Incorrect number of coordinates",
                                    JOptionPane.ERROR_MESSAGE);
                            System.exit(-1);
                        }
                    case 2:
                        try {

                            // Add the map point to the line strip
                            lineStrip.add(new Point(new Coord3d(
                                    Float.valueOf(rowHolder[0]),
                                    Float.valueOf(rowHolder[1]),
                                    0.0
                            )));
                        } catch (NumberFormatException e) {

                            // Throw error if a map point coordinate cannot be converted to a Float
                            String malformedCoordinateError = "Error on line: " + lineNumber + newline +
                                    "Coordinate is incorrectly formatted";
                            JOptionPane.showMessageDialog(null, malformedCoordinateError, "Incorrect Format",
                                    JOptionPane.ERROR_MESSAGE);
                            e.printStackTrace();
                            System.exit(-1);
                        }
                        break;
                    default:

                        // Throw error if the map point has more than three coordinates
                        String numCoordinateError = "Error on line: " + lineNumber + newline +
                                "The row contains " + rowHolder.length + " coordinates";
                        JOptionPane.showMessageDialog(null, numCoordinateError, "Incorrect number of coordinates",
                                JOptionPane.ERROR_MESSAGE);
                        System.exit(-1);
                }

                // Add the final lineStrip after while loop is complete.
                worldMapLineStrips.add(lineStrip);
                lineNumber ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("WARNING: World map file not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
