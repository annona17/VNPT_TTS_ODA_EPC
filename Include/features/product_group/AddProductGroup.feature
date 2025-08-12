
Feature: Add Product Group


  @Valid
  Scenario Outline: Add product group successfully
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive required notifications with text <text> 
    And I navigate to edit this product group
    #And Record in collection <collectionName> with name <name> and code <code> is in database

    Examples: 
      |collectionName	| name  			| code 				| description  			| text 																	|
      |ProductGroup		| Gói data 01 |   data01   	| Goi data thang 1 	| Create new product group successfully	|
      |ProductGroup		|	Gói data 02 |   data02 		| 								 	|	Create new product group successfully	|
      |ProductGroup		|	Gói data 05 |   data05 		| 								 	|	Create new product group successfully	|
      
      
  @Invalid_1.1_Empty_name
  Scenario Outline: Add product group when empty require field name
    Given I login ODA successful
    When I navigate to add product group form 
   	And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive compulsory name notifications
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName	| name  			| code 		| description  				|
      |ProductGroup		| 					 	| data03  | Goi data thang 03 	|
      |ProductGroup		|							|					|											|
   
   @Invalid_1.2_Emplty_code
  Scenario Outline: Add product group when empty require field code
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive compulsory code notifications
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName	| name  			| code 		| description  			|
      |ProductGroup		|	Gói data 03 |  				| Goi data thang 03 |
      
   @Invalid_2.1_Same_name
  Scenario Outline: Add product group when enter the same name
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive required notifications with text <text> 
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName	| name  			| code 			| description  			| text 						|
      |ProductGroup		| Gói data 01 | data_1   	| Goi data thang 1 	| Name is unique 	|	
      |ProductGroup		| Gói data 02	| data2			| Goi data thang 2	| Name is unique	|
   
   @Invalid_2.2_Same_code
  Scenario Outline: Add product group when enter the same code
    Given I login ODA successful
    When I navigate to add product group form 
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive required notifications with text <text>
   #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName	| name  			| code 			| description  			| text 						|
      |ProductGroup		| Gói data 03	|	data02		| Goi data thang 3	| Code is unique	|

      
   
      
     