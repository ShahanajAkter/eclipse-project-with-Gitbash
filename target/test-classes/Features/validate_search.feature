Feature: Search

  Scenario: Validate search feature
    Given Navigate to the amazom homepage
    When I click search field
    And Type "computer" on search field
    Then click search button
    And list of computer will displayed
