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
