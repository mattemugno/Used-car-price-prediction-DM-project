package autoscout24;

// 47 brands
public class Brand {
    /*
    public static final String[] BRANDS = { "Audi", "BMW", "Ford", "Mercedes-Benz", "Opel", "Renault", "Volkswagen",
            "Alfa Romeo", "Aston Martin", "Chevrolet", "Chrysler", "Citroen","Cupra", "Dacia",
            "Daihatsu", "Dodge", "Ferrari", "Fiat", "Honda", "Hyundai", "Infiniti", "Jaguar", "Jeep",
            "Kia","Lamborghini", "Lancia", "Land Rover", "Lexus", "Maserati", "Mazda", "MINI", "Mitsubishi",
            "Nissan", "Peugeot", "Porsche", "SEAT", "Skoda", "smart", "Subaru", "Suzuki", "Tesla",
            "Toyota", "Volvo"};*/

    /*public static final String[] BRANDS = { "Audi", "BMW", "Ford", "Mercedes-Benz", "Opel",
            "Renault", "Volkswagen", "Alfa Romeo", "Chevrolet", "Chrysler", "Citroen","Cupra",
            "Dacia", "Daihatsu", "Dodge", "Fiat", "Honda", "Hyundai", "Infiniti", "Jeep",
            "Kia", "Lancia", "Lexus", "Mazda", "MINI", "Mitsubishi",
            "Nissan", "Peugeot", "SEAT", "Skoda", "smart", "Suzuki",
            "Toyota", "Volvo"};*/

    // Fiat panda o 500
    // Audi a1
    // Bmw X3
    // Volvo Xc40
    // Citroen C4 aircross o C5 aircrosz
    // Toyota yaris

    /*public static final String[] BRANDS = { "Audi", "BMW", "Ford", "Volkswagen",
            "Citroen", "Renault", "Fiat", "Opel", "Toyota", "Volvo"};*/

    public static final String[] BRANDS = { "Aston Martin", "Ferrari", "Jaguar", "Lamborghini", "Maserati", "Porsche",
                                        "Bentley", "Bugatti", "Koenigsegg", "McLaren", "Pagani", "Rolls-Royce"};

    public static final String[] MODELS = {
            "A3", "A1", "X1", "X3", "Fiesta", "Focus", "Golf", "Polo",
            "C3", "C4", "Captur", "500", "Corsa", "Yaris", "XC40"
    };


    // source https://www.carwow.co.uk/blog/most-and-least-reliable-car-brands-revealed#gref
            Integer[] ReliabilityScore = {
                    53, // audi
            53, // bmw
            67, // ford
            56, // mercedes
            62, // opel
            67, // renault
            62, // vw
            53, // alfa
            60, // aston martin
            53, // chevrolet
            71,  // chrysler
            62, // citroen
            62,  // cupra
            85, // dacia
            80,  // daihatsu
            70,  // dodge
            40,  // ferrari
            71, // fiat
            87, // honda
            71, // hyundai
            64, // infiniti
            51, // jaguar
            71, // jeep
            69, // kia
            60, // lamborghini
            71, // lancia
            38, // land rover
            87, // lexus
            60,  // maserati
            67, // mazda
            60, // mini
            58, // mitsubishi
            64, // nissan
            64, // peugeot
            40,  // porsche
            62, // seat
            64, // skoda
            73, // smart
            80,  // subaru
            71, // suzuki
            50,  // tesla
            80, // toyota
            62}; // volvo
}
