Feature: Conference Track management with lightening duration
  Planning of conference tracks with lightening duration

  Scenario: Planning of conference tracks with lightening duration
    Given 'Writing Fast Tests Against Enterprise Rails' 12l
    And 'Overdoing it in Python' 9l
    And 'Lua for the Masses' 6l
    And 'Ruby Errors from Mismatched Gem Versions' 9l
    And 'Common Ruby Errors' 9l
    And 'Rails for Python Developers lightning'
    And 'Communicating Over Distance' 12l
    And 'Accounting-Driven Development' 9l
    And 'Woah' 6l
    And 'Sit Down and Write' 6l
    And 'Pair Programming vs Noise' 9l
    And 'Rails Magic' 12l
    And 'Ruby on Rails: Why We Should Move On' 12l
    And 'Clojure Ate Scala (on my project)' 9l
    And 'Programming in the Boondocks of Seattle' 6l
    And 'Ruby vs. Clojure for Back-End Development' 6l
    And 'Ruby on Rails Legacy App Maintenance' 12l
    And 'A World Without HackerNews' 6l
    And 'User Interface CSS in Rails Apps' 6l

    When Schedule 2 Track for proposed total 19 talks, 18 timed talks, 1 untimed talk

    Then Track 1 should have 180mins for morning session
    And Track 2 should have 180mins for morning session
    And Track 1 should have 240mins for afternoon session
    And Track 2 should less than 240mins for afternoon session
    And Track 2 should have 'Rails for Python Developers lightning' talk
    And Track 1 Lunch should start at 12:00PM
    And Track 2 Lunch should start at 12:00PM
    And Track 1 Networking Event should start at 05:00PM
    And Track 2 Networking Event should start at 05:00PM

    Then Track 1 : 09:00AM 'Writing Fast Tests Against Enterprise Rails' 60min
    And Track 1 : 10:00AM 'Communicating Over Distance' 60min
    And Track 1 : 11:00AM 'Rails Magic' 60min
    And Track 1 : 12:00PM 'Lunch'
    And Track 1 : 01:00PM 'Ruby on Rails: Why We Should Move On' 60min
    And Track 1 : 02:00PM 'Ruby on Rails Legacy App Maintenance' 60min
    And Track 1 : 03:00PM 'Overdoing it in Python' 45min
    And Track 1 : 03:45PM 'Ruby Errors from Mismatched Gem Versions' 45min
    And Track 1 : 04:30PM 'Lua for the Masses' 30min
    And Track 1 : 05:00PM 'Networking Event'

    And Track 2 : 09:00AM 'Common Ruby Errors' 45min
    And Track 2 : 09:45AM 'Accounting-Driven Development' 45min
    And Track 2 : 10:30AM 'Pair Programming vs Noise' 45min
    And Track 2 : 11:15AM 'Clojure Ate Scala (on my project)' 45min
    And Track 2 : 12:00PM 'Lunch'
    And Track 2 : 01:00PM 'Woah' 30min
    And Track 2 : 01:30PM 'Sit Down and Write' 30min
    And Track 2 : 02:00PM 'Programming in the Boondocks of Seattle' 30min
    And Track 2 : 02:30PM 'Ruby vs. Clojure for Back-End Development' 30min
    And Track 2 : 03:00PM 'A World Without HackerNews' 30min
    And Track 2 : 03:30PM 'User Interface CSS in Rails Apps' 30min
    And Track 2 : 04:00PM 'Rails for Python Developers lightning'
    And Track 2 : 05:00PM 'Networking Event'
