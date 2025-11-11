Feature: SIM card activation

Scenario: Activate SIM with valid ICCID
Given a SIM card with ICCID "1255789453849037777" and email "user@example.com"
When I POST to "/activate"
  Then the response should contain "Saved SIM with ID:"
When I GET with an ID of 1
  Then the response should contain '"active":true}'

  Scenario: Activate SIM with invalid ICCID
Given a SIM card with ICCID "8944500102198304826" and email "user@example.com"
When I POST to "/activate"
  Then the response should contain "Saved SIM with ID:"
When I GET with an ID of 2
  Then the response should contain '"active":false}'

