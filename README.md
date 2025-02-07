# BPM Calculator

This project is a BPM (Belasting van Personenauto's en Motorrijwielen) calculator built with Java, Spring Boot, and
Maven. It allows users to calculate the BPM for vehicles based on various inputs.
It is mainly intended for the Dutch market, especially for calculating the BPM for 
campers that are converted from commercial vehicles.

Dit project is een BPM (Belasting van Personesuito's en Motorrijwielen) calculator gebouwd met Java, Spring Boot en
Maven. Hiermee kunnen gebruikers de BPM berekenen voor voertuigen op basis van verschillende ingangen.
Het is vooral bedoeld voor de Nederlandse markt, met name voor het berekenen van de BPM voor
campers die zijn omgebouwd van bedrijfsvoertuigen.

## Features

- Calculate BPM based on vehicle catalog value, fuel type, and depreciation percentage.
- User-friendly web interface with form validation.
- Detailed calculation breakdown.

## Technologies Used

- Java
- Spring Boot
- Maven
- JavaScript
- Thymeleaf

## Getting Started

### Prerequisites

- Java 21 or higher
- Maven

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/bluesman80/bpm-calculator-java.git
    cd bpm-calculator-java
    ```

2. Set up the environment variables:
    ```sh
    export RDW_API_TOKEN=your_rdw_api_token
    ```

3. Build the project:
    ```sh
    mvn clean install
    ```

4. Run the application:
    ```sh
    mvn spring-boot:run
    ```

5. Access the application at `http://localhost:8080/bpm`.

## Configuration

Configuration settings can be found in `src/main/resources/application.properties`.

## Working Example

A working example of the BPM Calculator can be seen at [https://bpmcalculator.info](https://bpmcalculator.info).

## Usage

The BPM Calculator provides a web interface to calculate the BPM for converted camper vans.  Here's how to use it:

1.  **Open the Application:** Launch the application in your web browser by navigating to `http://localhost:8080/bpm` (after starting the Spring Boot application).

2.  **Choose Input Method:**  The form starts with a choice:

    *   **Automatisch via kenteken (Automatic via License Plate):**  This is the default option.
    Select this to retrieve vehicle information automatically using the Dutch license plate number.
    *   **Zelf cataloguswaarde invullen (Enter Catalog Value Manually):**  Choose this option if you want to directly input the vehicle's catalog value.
    This is useful if the license plate lookup fails or if you have the catalog value from another source.

3.  **Fill in the Form Fields:** The required fields will change depending on the input method selected.

    *   **If "Automatisch via kenteken" is selected:**
        *   **Kenteken (zonder strepjes) (License Plate (without dashes)):**
        Enter the 6-character Dutch license plate *without* any dashes or spaces (e.g., `XX123Y`). 
        The application will attempt to fetch the vehicle's catalog value and fuel type from the RDW (Dutch Vehicle Authority) 
        using the provided API token.

    *   **If "Zelf cataloguswaarde invullen" is selected:**
        *   **Totale Cataloguswaarde (€) (Total Catalog Value (€)):** Enter the vehicle's *total* catalog value (including BTW/VAT and BPM) in Euros. 
        Use the numeric input field, which accepts decimal values (e.g., `25000.50`).

    *   **Brandstoftype (Fuel Type):**  This section appears if entering the catalog value manually. 
    Select the fuel type. The selection here affects the final BPM calculation (as described in the "Calculation Details" section).

    *   **Afschrijvingspercentage (%) (Depreciation Percentage (%)):**  Enter the depreciation percentage as a number between 0 and 100.  
    This represents the reduction in BPM due to the vehicle's age. 
    The form provides a helpful link to the *Belastingdienst* (Dutch Tax Authority) website, 
    which offers a "forfaitaire tabel" (standardized table) to help determine the appropriate depreciation percentage.

4.  **Calculate BPM:**  Once all required fields are filled, click the **"Bereken BPM" (Calculate BPM)** button.

5.  **View Results:**  The "Berekening Resultaten" (Calculation Results) section will appear below the form. 
    This section shows the detailed breakdown of the BPM calculation, including the vehicle's catalog value, 
    the depreciation percentage, the calculated BPM, and the BPM per month (if applicable).

7.  **New Calculation:**  Click the **"Nieuwe Berekening" (New Calculation)** button to reset the form and perform another calculation.

**Important Considerations:**

*   **RDW API Token:**  The "Automatisch via kenteken" feature relies on a valid `RDW_API_TOKEN` being configured in your environment variables.
See the "Obtaining RDW API Token" section below for instructions.
*   **Error Handling:** The HTML includes basic form validation (using Bootstrap's `needs-validation` class and `required` attributes).
The provided `index.html` does not show how errors from the RDW API or server-side calculations are presented to the user.
TODO: Ideally, user-friendly error messages should be displayed.

## Obtaining RDW API Token

To obtain the `RDW_API_TOKEN`, follow these steps:

1. Register for an API key on the [RDW API portal](https://opendata.rdw.nl/profile/edit/developer_settings).
2. Sign up for an account if you don't have one.
3. Log in to your account and navigate to the API section.
4. Find the API you need access to and subscribe to it.
5. After subscribing, you will receive an API key (token).

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.

## Acknowledgements

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Thymeleaf](https://www.thymeleaf.org/)