Feature: Conference Track management Network event start time
  Planning of conference tracks Network event start time

  Scenario: Planning of conference tracks Network event start time
    Given 'Writing Fast Tests Against Enterprise Rails' 60min
    And 'Overdoing it in Python' 45min
    And 'Lua for the Masses' 30min
    And 'Ruby Errors from Mismatched Gem Versions' 45min
    And 'Common Ruby Errors' 45min
    And 'Communicating Over Distance' 60min
    And 'Accounting-Driven Development' 45min
    And 'Woah' 30min
    And 'Sit Down and Write' 30min
    And 'Pair Programming vs Noise' 45min
    And 'Rails Magic' 60min
    And 'Ruby on Rails: Why We Should Move On' 60min
    And 'Clojure Ate Scala (on my project)' 45min
    And 'Programming in the Boondocks of Seattle' 30min
    And 'Ruby vs. Clojure for Back-End Development' 30min
    And 'Ruby on Rails Legacy App Maintenance' 60min
    And 'A World Without HackerNews' 30min

    When Schedule 2 Track for proposed total 17 talks, 17 timed talks, 0 untimed talk

    Then Track 1 Networking Event should start at 05:00PM
    And Track 2 Networking Event should start at 04:00PM
