
Feature: Delete Product Group

  @Delete_ClickNo
  Scenario Outline: Delete a product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to product group
    When I delete a product group with code <code> 
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
    #And Record in collection <collectionName> with code <code> is still in database
    
    Examples: 
    	| collectionName| code 		|
      |ProductGroup		| data02	|
      |ProductGroup		| data01	|
      
  @Delete_ClickNo2
  Scenario Outline: Delete many product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to product group
    When I tick 2 product groups with code1 <code1> and code2 <code2> 
  	And I click icon button delete   
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
   #	And Record in collection <collectionName> with code1 <code1> and code2 <code2> is still in database
    
    Examples: 
    	| collectionName| code1 	| code2 	|
      |ProductGroup		| data02	| data04	|
  
  @Valid1
  Scenario Outline: Delete 1 product group not active 
    Given I login ODA successful
    And I navigate to product group
    When I delete a product group with code <code> 
    And I click Yes in confirm delete popup
    Then I receive a delete successfully notification 
    Then I receive notification with text <text>
    #And Record in collection <collectionName> with code <code> is not in database

    Examples: 
    	| collectionName| code 		| text 																					|
      |ProductGroup		| data01	| Delete selected product group successfully. 	|
      
  @Valid2
  Scenario Outline: Delete many product groups not active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I tick 2 product groups with code1 <code1> and code2 <code2>
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive a delete successfully notification 
  	Then I receive notification with text <text>
  #	And Record in collection <collectionName> with code1 <code1> and code2 <code2> are not in database
  
  	Examples: 
    	| collectionName| code1   | code2  	| text 																					|
      |ProductGroup		| data02	| data05	| Delete selected product groups successfully. 	|
      
  @Valid3 
  Scenario Outline: Delete all product group not active 
  	Given I login ODA successful
  	And I navigate to product group
  	When I tick checkbox all 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive a delete successfully notification 
  	Then I receive notification with text <text>
  #	And Selected record in collection <collectionName> are not in database
  
  	Examples: 
  		| collectionName 	| text 																						|
  		| ProductGroup		|  Delete selected product groupa successfully. 	|
  	
  @Invalid0 
  Scenario Outline: Delete when choose no product group 
  	Given I login ODA successful
  	And I navigate to product group
  	When I click delete button  
  	Then I receive notification with text <text>
  #	And No No record in collection <collectionName> is deleted
 
  	Examples: 
  		| collectionName 	| text 											|
  		| ProductGroup		| No records selected yet.	|
  
  @Invalid1
  Scenario Outline: Delete 1 product group in active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I delete a product group with code <code>
  	And I click Yes in confirm delete popup 
  	Then I receive notification with text <text>
  #	And Selected record in collection <collectionName> with code <code> is still in database 
  
  	Examples: 
    	| collectionName 	| code 		| text 														|
      | ProductGroup		| VIPx99	| Cannot delete selected record. 	|
  
  @Invalid2
  Scenario Outline: Delete many product groups contain product group in active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I tick 2 product groups with code1 <code1> and code2 <code2>
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text> 
  #	And Selected record in collection <collectionName> with code1 <code1> and code2 <code2> is still in database  
  	
  	Examples: 
    	| collectionName 	| code1 	| code2 	| text 															|
      | ProductGroup		| data04	| VIPx99	| Cannot delete selected records. 	|
  
  	
  @DeleteAll_Invalid 
  Scenario Outline: Delete all product group but contain product group active 
  	Given I login ODA successful
  	And I navigate to product group
  	When I tick checkbox all 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive notification with text <text>  
  	#	And Selected record in collection <collectionName> with code1 <code1> and code2 <code2> is still in database
  
		Examples: 
    	| collectionName 	| text 															|
      | ProductGroup		| Cannot delete selected records. 	|
  	
  