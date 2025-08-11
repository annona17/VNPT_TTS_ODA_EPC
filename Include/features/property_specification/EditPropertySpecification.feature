
Feature: Edit Property specification 

  @Valid
  Scenario Outline: Edit Property specification successfully 
    Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode> 
    And I edit name <name>, code <code>, description <description>, startDate <startDate>, endDate <endDate>, status <status>, version <version> 
    And I click button Save 
    Then I receive notification with text <text> 
    #And Record in collection <collectionName> with oldCode <oldCode> is updated with name <name>, code <code>, description <description>, startDate <startDate>, endDate <endDate>, status <status>, version <version> in database
     

    Examples: 
  		|collectionName				| oldCode	| name 						| code		| description 	| startDate 	| endDate 	 	| status 		| version 	| text 																				|
  		|PropertySpecification| tt01		| Thuộc tính 11		| tt01		|								| 						| 						|						| 		   	 	| Property specification updated successfully	|
      |PropertySpecification| tt01		| Thuộc tính 11		| tt11		|								| 						| 						|						| 		   	 	| Property specification updated successfully	| 
      |PropertySpecification| tt11		| Thuộc tính 11		| tt11		|	Thuộc tính 11	| 						| 						|						| 					| Property specification updated successfully	|
      |PropertySpecification| tt02		| Thuộc tính 12   | tt12		|	Thuộc tính 12	| 01/01/2026	| 						|						| 					| Property specification updated successfully	|
      |PropertySpecification| tt12		| Thuộc tính 12		| tt12		|	Thuộc tính 12	| 01/01/2026	| 31/12/2026	|						| 					| Property specification updated successfully	|
      |PropertySpecification| tt12		| Thuộc tính 12		| tt12		|	Thuộc tính 12	| 01/01/2026	| 31/12/2026	|	In design	| 					|	Property specification updated successfully	|
      |PropertySpecification| tt12		| Thuộc tính 12		| tt12		|	Thuộc tính 12	| 01/01/2026	| 31/12/2026	|	In design	| 	1				|	Property specification updated successfully	|
      
   
   @Invalid
   Scenario Outline: Edit Property specification when empty name 
   	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode>
    #And I save old record with oldCode <oldCode> in snapshot 
   	And I empty name
    And I click button Save 
    Then I receive required notifications with text <text>  
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated in database
    
   	Examples: 
		   |collectionName				|oldCode| text							|
		   |PropertySpecification	|tt11		| Name is required 	|
   
   @Invalid
   Scenario Outline: Edit Property specification when empty code 
   	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode>
   	And I empty code
    And I click button Save 
    Then I receive required notifications with text <text>  
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated in database when empty code
    
   	Examples: 
		   |collectionName				|oldCode| text 							|
		   |PropertySpecification	|tt11		|	Code is required	|
   	
   @Invalid
   Scenario Outline: Edit Property specification when enter the same name 
   	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode>
   	And I edit name <name> same other Property specification 
    And I click button Save 
    Then I receive notification with text <text>   
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated in database
   
    Examples: 
  		|collectionName				| oldCode | name 				| text						|
      |PropertySpecification| tt11		| VIPx99			|	Name is unique	|				 
    
   @Invalid
   Scenario Outline: Edit Property specification when enter the same code 
   	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode>
   	And I edit code <code> same other Property specification 
    And I click button Save 
    Then I receive notification with text <text>  
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated code <code> in database
   
    Examples: 
  		|collectionName				| oldCode | code 	| text						|
      |PropertySpecification| tt12		| VIPx99|	Code is unique	|						
		
   @Invalid
   Scenario Outline: Edit Property specification when enter startDate < today 
  	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode>
    And I edit startDate <startDate> < today  
    And I click button Save 
    Then I receive notification with text <text>  
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated in database
    
    Examples: 
  		|collectionName				| oldCode | startDate 	| text 																			|
      |PropertySpecification| tt12		| 01/01/2025	| The start date must be greater than today	|
      				
   @Invalid
   Scenario Outline: Edit Property specification when enter startDate > endDate 
  	Given I login ODA successful
    And I navigate to Property Specification
    When I edit Property specification form with oldCode <oldCode> 
    And I edit startDate <startDate> < endDate <endDate>  
    And I click button Save 
    Then I receive notification with text <text>  
    #And Record in collection <collectionName> with oldCode <oldCode> is not updated in database
    
    Examples: 
  		|collectionName				| oldCode | startDate 	| endDate 		| text 																							|
      |PropertySpecification| tt12		| 01/01/2026	| 31/12/2025 	| The start date must be smaller than the end date 	|
   
   