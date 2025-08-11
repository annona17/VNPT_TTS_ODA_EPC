Feature: Delete Property Specification
  @Delete_ClickNo
  Scenario Outline: Delete a product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to Property Specification
    When I delete a Property Specification with code <code> 
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
    #And Record in collection <collectionName> with code <code> is still in database
    
    Examples: 
    	| collectionName					| code 	|
      |PropertySpecification		| tt02	|
      |PropertySpecification		| tt01	|
      
  @Delete_ClickNo2
  Scenario Outline: Delete many product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to Property Specification
    When I tick 2 Property Specifications with code1 <code1> and code2 <code2> 
  	And I click icon button delete   
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
   #	And Record in collection <collectionName> with code1 <code1> and code2 <code2> is still in database
    
    Examples: 
    	| collectionName					| code1 	| code2 	|
      |PropertySpecification		| tt02		| tt04		|
  
  @Valid1
  Scenario Outline: Delete 1 Property Specification not active 
    Given I login ODA successful
    And I navigate to Property Specification
    When I delete a Property Specification with code <code> 
    And I click Yes in confirm delete popup
    Then I receive notification with text <text> 
    #And Record in collection <collectionName> with code <code> is not in database

    Examples: 
    	| collectionName					| code 	|	 text 																								|
      |PropertySpecification		| tt01	|  Delete selected property specification successfully.	|
      
  @Valid2
  Scenario Outline: Delete many Property Specifications not active 
  	Given I login ODA successful
  	And I navigate to Property Specification 
  	When I tick 2 Property Specifications with code1 <code1> and code2 <code2>
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text> 
  #	And Record in collection <collectionName> with code1 <code1> and code2 <code2> are not in database
  	Examples: 
    	| collectionName					| code1 | code2 |  text 																									|
      |PropertySpecification		| tt02	| tt05	|  Delete selected property specifications successfully.	|
      
  @Valid3 
  Scenario Outline: Delete all Property Specification not active 
  	Given I login ODA successful
  	And I navigate to Property Specification
  	When I tick checkbox all 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text>  
  #	And Selected record in collection <collectionName> are not in database
  
  Examples: 
  | collectionName 					| text 																									|
  | PropertySpecification		| Delete selected property specifications successfully.	|
  	
  @Invalid0 
  Scenario Outline: Delete when choose no Property Specification 
  	Given I login ODA successful
  	And I navigate to Property Specification
  	When I click delete button 
  	Then I receive notification with text <text> 
  #	And No record in collection <collectionName> is deleted
 
  Examples: 
  | collectionName 					| text 											|
  | PropertySpecification		| No records selected yet.	|
  
  @Invalid1
  Scenario Outline: Delete 1 Property Specification in active 
  	Given I login ODA successful
  	And I navigate to Property Specification 
  	When I delete a Property Specification with code <code>
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text> 
  #	And Selected record in collection <collectionName> with code <code> is still in database 
  
  	Examples: 
    	| collectionName 					| code 		|  text														|
      | PropertySpecification		| VIPx99	| Cannot delete selected record.	|
  
  @Invalid2
  Scenario Outline: Delete many Property Specifications contain Property Specification in active 
  	Given I login ODA successful
  	And I navigate to Property Specification 
  	When I tick 2 Property Specifications with code1 <code1> and code2 <code2>
  	And I click icon button delete  
  	Then I receive notification with text <text> 
  #	And Selected record in collection <collectionName> with code1 <code1> and code2 <code2> is still in database  
  	
  	Examples: 
    	| collectionName 					| code1 	| code2 	| text														|
      | PropertySpecification		| tt04	| VIPx99	| Cannot delete selected records.	|
  
  	
  @DeleteAll_Invalid 
  Scenario Outline: Delete all Property Specification but contain Property Specification active 
  	Given I login ODA successful
  	And I navigate to Property Specification
  	When I tick checkbox all 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text> 
  
 	 Examples: 
    	| collectionName 					| text 														|
      | PropertySpecification		| Cannot delete selected records.	|
  


  	
  