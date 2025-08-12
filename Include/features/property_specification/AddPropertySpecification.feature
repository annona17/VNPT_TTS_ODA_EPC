
Feature: Add Property Specification


  @Valid
  Scenario Outline: Add Property Specification successfully
    Given I login ODA successful
    And I navigate to Property Specification 
    When I click button Add to add new Property Specification  
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive notification with text <text> 
    And I navigate to edit this Property Specification
    #And Record in collection <collectionName> with name <name> and code <code> is in database

    Examples: 
      |collectionName				| name  				| code 		| description  	|  text																						|
      |PropertySpecification| Thuoc tinh 01 | tt01   	| Thuộc tính 1 	| Create new property specification successfully	|
      |PropertySpecification|	Thuoc tinh 02 | tt02 		| 							|	Create new property specification successfully	|
      |PropertySpecification|	Thuoc tinh 02 | tt05 		| 							|	Create new property specification successfully	|
      
      
  @Invalid_1.1_Empty_name
  Scenario Outline: Add product group when empty require field name
    Given I login ODA successful
    And I navigate to Property Specification 
    When I click button Add to add new Property Specification  
    And I enter name <name> code <code> and description <description>
    And I click save add button
    Then I receive required notifications with text <text>
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName				| name  			| code 	| description  		| text							|
      |PropertySpecification| 					 	| tt03  | Thuộc tính 03 	| Name is required	|
      |PropertySpecification|							|				|									|	Name is required	|
   
   @Invalid_1.2_Empty_code
  Scenario Outline: Add product group when empty require field code
    Given I login ODA successful
    And I navigate to Property Specification 
    When I click button Add to add new Property Specification  
   	And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive required notifications with text <text>
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName				| name  				| code 		| description  	| text							|
      |PropertySpecification|	Thuộc tính 03 |  				| Thuộc tính 03 | Code is required	|
      
   @Invalid_2.1_Same_name
  Scenario Outline: Add product group when enter the same name
    Given I login ODA successful
    And I navigate to Property Specification 
    When I click button Add to add new Property Specification 
    And I enter name <name> code <code> and description <description>
    And I click save add button 
    Then I receive notification with text <text> 
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName				| name  				| code 		| description  	| text 						|
      |PropertySpecification| Thuộc tính 01 | tt_1   	| Thuộc tính 01 | Name is unique 	|
      |PropertySpecification| Thuộc tính 02	| tt2			| Thuộc tính 02	|	Name is unique 	|
   
   @Invalid_2.2_Same_code
  Scenario Outline: Add product group when enter the same code
    Given I login ODA successful
    And I navigate to Property Specification 
    When I click button Add to add new Property Specification   
    And I enter name <name> code <code> and description <description>
    And I click save add button
    Then I receive required notifications with text <text>  
    #And Record in collection <collectionName> with name <name> and code <code> is NOT in database

    Examples: 
      |collectionName				| name  				| code 			| description  				| text						|
      |PropertySpecification| Thuộc tính 03	|	data02		| Thuộc tính thang 3	| Code is unique 	|

      
   
      
     