package autoscout24;

import java.util.Arrays;
import java.util.List;

public class Attributes {
    public static final List<String> ATTRIBUTES = Arrays.asList(
            "Model", "Price", "Manufacturer", "Country", "Fuel type", "Seller", "Body type", "Upholstery colour",
            "Type", "Doors", "Warranty", "Mileage", "Power", "Gearbox", "Drivetrain", "Other fuel types",
            "Comfort & Convenience", "Fuel consumption", "First registration", "Extras", "Full service history", "Colour",
            "Previous owner", "Safety & Security", "Cylinders", "Entertainment & Media", "Empty weight",
            "Engine size", "CO2 Emissions", "Seats", "Gears", "Emission class", "Upholstery"
            );

    // rel_score, mileage, prev_owner, warranty, Safety & Security, year
            // rating = (rel_score*safe_sec*(warranty + 1))/((mileage*10^-3)*(prev_owner + 1)*(current_year - year))
}
