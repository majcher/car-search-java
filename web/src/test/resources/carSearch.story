Scenario: Car search with more than one result

Given is the default data set
When I execute a search with more than one search result
Then the page should contain for each found car the image, make, model, color and the price

Scenario: Car search with input search phrase

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

