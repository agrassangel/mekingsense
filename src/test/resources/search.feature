Feature: Mercado Libre
#  As a customer i want to search a car in the system

  Background:
    Given User is on mercado Libre web page


  Scenario Outline: Search a car
    Given  The user is in "<country>" branch
    And User is on category "<submenu>" view
    And User has the required query filters "<query>"
    When User set the required query filters
    Then System will show the result according to the criteria
    Examples:
      | country   | submenu   | query                                                          |
      | Argentina | Vehículos | Precio_Max:2000000,Ordenar por:Mayor precio, Ubicación:Córdoba |