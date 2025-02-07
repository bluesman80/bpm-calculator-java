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

## Usage

1. Open the application in your browser.
2. Fill in the required fields in the form.
3. Click "Bereken BPM" to calculate the BPM.
4. View the detailed calculation results.

## Working Example

A working example of the BPM Calculator can be seen at [https://bpmcalculator.info](https://bpmcalculator.info).

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
- [Bootstrap](https://getbootstrap.com/)
- [Thymeleaf](https://www.thymeleaf.org/)