package demo.worldmap;

import org.jzy3d.analysis.AbstractAnalysis;
import org.jzy3d.analysis.AnalysisLauncher;
import org.jzy3d.chart.factories.AWTChartComponentFactory;
import org.jzy3d.maths.BoundingBox3d;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.primitives.axes.layout.IAxeLayout;
import org.jzy3d.plot3d.primitives.axes.layout.providers.SmartTickProvider;
import org.jzy3d.plot3d.primitives.axes.layout.renderers.IntegerTickRenderer;
import org.jzy3d.plot3d.rendering.canvas.Quality;

public class WorldMapDemo extends AbstractAnalysis {
    public static void main(String[] args) throws Exception {
        AnalysisLauncher.open(new WorldMapDemo());
    }

    public void init(){

        // Create the world map chart
        chart = AWTChartComponentFactory.chart(Quality.Advanced, "newt");

        // Instantiate world map and parse the file
        WorldMap worldMap = new WorldMap();
        worldMap.parseFile();

        // Add world map line stripe to chart
        chart.getScene().getGraph().add(worldMap.worldMapLineStrips);

        // Set axis labels for chart
        IAxeLayout axeLayout = chart.getAxeLayout();
        axeLayout.setXAxeLabel("Longitude (deg)");
        axeLayout.setYAxeLabel("Latitude (deg)");
        axeLayout.setZAxeLabel("Altitude (km)");

        // Set precision of tick values
        axeLayout.setXTickRenderer(new IntegerTickRenderer());
        axeLayout.setYTickRenderer(new IntegerTickRenderer());
        axeLayout.setZTickRenderer(new IntegerTickRenderer());

        // Define ticks for axis
        axeLayout.setXTickProvider(new SmartTickProvider(10));
        axeLayout.setYTickProvider(new SmartTickProvider(10));
        axeLayout.setZTickProvider(new SmartTickProvider(10));

        // Create bounding box and set to chart
        BoundingBox3d bounds = new BoundingBox3d(-180, -140, 40, 80, 0, 1000);
        chart.getView().setBoundManual(bounds);

        // Set map viewpoint
        chart.getView().setViewPoint(new Coord3d(-2 * Math.PI / 3, Math.PI / 4, 0));
    }
}
