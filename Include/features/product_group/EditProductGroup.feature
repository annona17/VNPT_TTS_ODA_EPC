
Feature: Edit Product group 

  @Valid
  Scenario Outline: Edit product group successfully 
    Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode> 
    And I edit valid name <name>, code <code>, description <description>, startDate <startDate>, endDate <endDate>, status <status>, version <version> 
    And I click button Save 
    Then I receive edit successfully notification 
    #And This product group with oldCode <oldCode> is updated with name <name>, code <code>, description <description>, startDate <startDate>, endDate <endDate>, status <status>, version <version> in database
     

    Examples: 
  		| oldCode	| name 								| code			| description 		 | startDate | endDate 	 | status | version 	|
      | data01	| Gói data tháng 1		| data11		|	Gói data tháng 11| 01/01/2026| 31/12/2026|In study| 	1   	 	|  
      | data11	| Gói data tháng 11		| data11		|									 | 01/01/2026| 31/12/2026|In study| 	1				| 
      | data02	| Gói data tháng 12   | data12		|									 | 01/01/2026| 31/12/2026|In study| 	1				| 
      | data05	| Gói data tháng 07		| data07		|									 | 					 | 					 |In study| 					|
      
   
   @Invalid
   Scenario Outline: Edit product group when empty name 
   	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode>
    #And I save old record with oldCode <oldCode> in snapshot 
   	And I empty name
    And I click button Save 
    Then I receive require name notification  
    #And This product group with oldCode <oldCode> is not updated in database
    
   	Examples: 
		   |oldCode	|
		   |data01	|
   
   @Invalid
   Scenario Outline: Edit product group when empty code 
   	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode>
   	And I empty code
    And I click button Save 
    Then I receive require code notification  
    #And This product group with oldCode <oldCode> is not updated in database when empty code
    
   	Examples: 
		   |oldCode	|
		   |data01	|				
   	
   @Invalid
   Scenario Outline: Edit product group when enter the same name 
   	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode>
   	And I edit name <name> same other product group 
    And I click button Save 
    Then I receive unique name notification  
    #And This product group with oldCode <oldCode> is not updated in database
   
    Examples: 
  		| oldCode 	| name 				|
      | data01		| VIPx99			|							 
    
   @Invalid
   Scenario Outline: Edit product group when enter the same code 
   	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode>
   	And I edit code <code> same other product group 
    And I click button Save 
    Then I receive unique code notification  
    #And This product group with oldCode <oldCode> is not updated code <code> in database
   
    Examples: 
  		| oldCode 	| code 	|
      | data02		| VIPx99|							
		
   @Invalid
   Scenario Outline: Edit product group when enter startDate < today 
  	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode>
    And I edit startDate <startDate> < today  
    And I click button Save 
    Then I receive invalid start date notification  
    #And This product group with oldCode <oldCode> is not updated in database
    
    Examples: 
  		| oldCode 	| startDate 	|
      | data02		| 01/01/2025	| 	
      				
   @Invalid
   Scenario Outline: Edit product group when enter startDate > endDate 
  	Given I login ODA successful
    And I navigate to product group
    When I edit product group form with oldCode <oldCode> 
    And I edit startDate <startDate> < today  
    And I click button Save 
    Then I receive invalid date notification  
    #And This product group with oldCode <oldCode> is not updated in database
    
    Examples: 
  		| oldCode 	| startDate 	| endDate 		|
      | data02		| 01/01/2026	| 31/12/2025 	|
   