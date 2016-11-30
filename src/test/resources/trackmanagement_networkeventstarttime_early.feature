Feature: Conference Track management Network event start time
  Planning of conference tracks Network event start time

  Scenario: Planning of conference tracks Network event start time
    Given 'Writing Fast Tests Against Enterprise Rails' 60min
    And 'Overdoing it in Python' 30min
    And 'Lua for the Masses' 60min

    And 'Common Ruby Errors' 60min
    And 'Communicating Over Distance' 60min
    And 'Accounting-Driven Development' 60min
    And 'Ruby Errors from Mismatched Gem Versions' 45min

    When Schedule 1 Track for proposed total 7 talks, 7 timed talks, 0 untimed talk

    Then Track 1 Networking Event should start at 04:15PM
