Feature: Conference Track management Exception
  Planning of conference tracks Exception

  Scenario: Planning of conference tracks Exception
    Given 'Overdoing it in Python' -5min
    And 'Overdoing it in Python 2' 241min

    When Schedule 0 Track for proposed total 0 talks, 0 timed talks, 0 untimed talk

    Then 0 Track is created
    And 2 Illegal Argument Exception is raised
