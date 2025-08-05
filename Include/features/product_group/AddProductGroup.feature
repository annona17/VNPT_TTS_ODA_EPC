
Feature: Add Product Group


  @Valid
  Scenario Outline: Add product group successfully
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter valid name <name> code <code> and description <description>
    And I click save button 
    Then I receive successful notification 
    And I navigate to edit this product group
    And This product group with name <name> and code <code> is in database

    Examples: 
      | name  			| code 				| description  			|
      | Gói data 01 |   data01   	| Goi data thang 1 	|
      |	Gói data 02 |   data02 		| 								 	|
      
      
  @Invalid_1.1_Empty_name
  Scenario Outline: Add product group when empty require field name
    Given I login ODA successful
    When I navigate to add product group form 
    And I empty name <name> and enter valid code <code>, enter valid description <description>
    And I click save button 
    Then I receive compulsory name notifications
    And This product group with name <name> and code <code> is NOT in database

    Examples: 
      | name  			| code 		| description  			|
      | 					 	| data03  | Goi data thang 03 	|
      |							|					|										|
   
   @Invalid_1.2_Emplty_code
  Scenario Outline: Add product group when empty require field code
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter valid name <name>, empty code <code> and enter valid description <description>
    And I click save button 
    Then I receive compulsory code notifications
    And This product group with name <name> and code <code> is NOT in database

    Examples: 
      | name  			| code 		| description  			|
      |	Gói data 03 |  				| Goi data thang 03 |
      
   @Invalid_2.1_Same_name
  Scenario Outline: Add product group when enter the same name
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter the same name <name> and enter valid code <code>, enter valid description <description>
    And I click save button 
    Then I receive exist name notification 
    And This product group with name <name> and code <code> is NOT in database

    Examples: 
      | name  			| code 			| description  			|
      | Gói data 01 | data_1   	| Goi data thang 1 	|
      | Gói data 02	| data2			| Goi data thang 2	|
   
   @Invalid_2.2_Same_code
  Scenario Outline: Add product group when enter the same code
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter valid name <name>, enter the same code <code> and enter valid description <description>
    And I click save button 
    Then I receive exist code notification 
    And This product group with name <name> and code <code> is NOT in database

    Examples: 
      | name  			| code 			| description  			|
      | Gói data 03	|	data02		| Goi data thang 3	|

      
   
      
     