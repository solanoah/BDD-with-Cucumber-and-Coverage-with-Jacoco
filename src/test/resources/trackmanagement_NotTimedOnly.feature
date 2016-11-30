Feature: Conference Track management NotTimed Only
  Planning of conference tracks NotTimed Only

  Scenario: Planning of conference tracks NotTimed Only
    Given 'Rails for Python Developers lightning'

    When Schedule 1 Track for proposed total 1 talks, 0 timed talks, 1 untimed talk

    Then Track 1 should have 'Rails for Python Developers lightning' talk
    And Track 1 : 09:00AM 'Rails for Python Developers lightning'
