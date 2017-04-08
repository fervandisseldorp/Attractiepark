package B2a.domain.attraction;

public class WaterBuilder extends AttractionBuilder {
    Attraction waterAttraction;

    @Override
    public Attraction createNewAttraction() {
        waterAttraction = new WaterAttraction();
        waterAttraction.setName("Waterrrride");
        waterAttraction.setDuration( 4 );
        waterAttraction.setAmountStaff(3);
        waterAttraction.setMinimumHeight( 100 );
        waterAttraction.setTransportType("Log boats");
        waterAttraction.customSetImage("waterattraction");

        return waterAttraction;
    }
}
