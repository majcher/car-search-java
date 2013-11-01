Scenario: Car search with more than one result

Given is the default data set
When I execute a search with more than one search result
Then the page should contain for each found car the image, make, model, color and the price

Scenario: Car search by make

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input <phrase>
And I execute the search
Then the web application shows a search result page showing cars by <make> in size of <count>

Examples:
|phrase|make|count|
|Ford|Ford|1|
|Buick|Buick|1|
|BMW|BMW|1|
|Audi|Audi|2|

Scenario: Car search by model

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input <phrase>
And I execute the search
Then the web application shows a search result page showing cars <make> <model> in size of <count>

Examples:
|phrase|make|model|count|
|Mustang|Ford|Mustang|1|
|Lacrosse|Buick|Lacrosse|1|
|X5|BMW|X5|1|
|A4|Audi|A4|2|

Scenario: Car search by make and model

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input <phrase>
And I execute the search
Then the web application shows a search result page showing cars <make> <model> in size of <count>

Examples:
|phrase|make|model|count|
|Ford Mustang|Ford|Mustang|1|
|Buick Lacrosse|Buick|Lacrosse|1|
|BMW X5|BMW|X5|1|
|Audi A4|Audi|A4|2|

