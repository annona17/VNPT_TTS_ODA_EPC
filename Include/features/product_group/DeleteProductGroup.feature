Feature: Delete Product Group
  @Delete_ClickNo
  Scenario Outline: Delete a product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to product group
    When I delete a product group with code <code> 
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
    And This product with code <code> is still in database
    
    Examples: 
    	| code 	|
      | data2	|
      | data1	|
      
  @Delete_ClickNo2
  Scenario Outline: Delete many product when click No in popup confirm dialog 
		Given I login ODA successful
    And I navigate to product group
    When I tick 2 product groups with code1 <code1> and code2 <code2> 
  	And I click icon button delete   
    And I click No in confirm delete popup
    Then Popup Confirm delete disappear 
   	And These product with code1 <code1> and code2 <code2> is still in database
    
    Examples: 
    	| code1 | code2 |
      | data3	| data2	|
  
  @Valid1
  Scenario Outline: Delete 1 product group not active 
    Given I login ODA successful
    And I navigate to product group
    When I delete a product group not active with code <code> 
    And I click Yes in confirm delete popup
    Then I receive a delete successfully notification 
    And This product with code <code> is not in database

    Examples: 
    	| code 	|
      | data3	|
      
  @Valid2
  Scenario Outline: Delete many product groups not active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I tick 2 product groups not active with code1 <code1> and code2 <code2>
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive a delete successfully notification 
  	And These product code1 <code1> and code2 <code2> are not in database
  	Examples: 
    	| code1 | code2 |
      | data2	| data1	|
      
  @Valid3 
  Scenario: Delete all product group not active 
  	Given I login ODA successful
  	And I navigate to product group
  	When I tick checkbox all 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive a delete successfully notification 
  	And Selected product groups are not in database
  	
  @Invalid0 
  Scenario: Delete when choose no product group 
  	Given I login ODA successful
  	And I navigate to product group
  	When I click delete button 
  	Then I receive a no product group selected notification 
  	And No product group is deleted
  
  @Invalid1
  Scenario Outline: Delete 1 product group in active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I delete product group active with code <code>
  	And I click Yes in confirm delete popup
  	Then I receive cannot delete notification 
  	And This product with code <code> is still in database 
  
  	Examples: 
    	| code 		|
      | VIPx99	|
  
  @Invalid2
  Scenario Outline: Delete many product groups contain product group in active 
  	Given I login ODA successful
  	And I navigate to product group 
  	When I tick 2 product groups active with code1 <code1> and code2 <code2> 
  	And I click icon button delete  
  	And I click Yes in confirm delete popup
  	Then I receive cannot delete notification 
  	And These product with code1 <code1> and code2 <code2> is still in database  
  	
  	Examples: 
    	| code1 	| code2 	|
      | data11	| data21	|
  
  
  	
  	
  #@DeleteAll_Invalid 
  #Scenario: Delete all product group but contain product group active 
  #	Given I login ODA successful
  #	And I navigate to product group
  #	When I tick checkbox all 
  #	And I click icon button delete  
  #	And I click Yes in confirm delete popup
  #	Then I receive cannot delete notification  
  


  	
  