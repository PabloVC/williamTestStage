Feature: Tech test stage William Hill possition
 
  Scenario: Five pence bet football event 
    Given Authentication on william hill
    When Bet 0.05 to "football"
    Then Assert to return, total stake and user amount
  
  Scenario: Twelve pence bet football event 
    Given Authentication on william hill
    When Bet 0.12 to "football"
    Then Assert to return, total stake and user amount
    
  Scenario: Twelve eigth bet tennis event 
    Given Authentication on william hill
    When Bet 0.08 to "tennis"
    Then Assert to return, total stake and user amount