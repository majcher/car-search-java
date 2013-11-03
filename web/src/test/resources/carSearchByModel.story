Scenario: Car search by model

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input car model <model>
And I execute the search
Then the web application shows a search result page showing cars <make> <model> in size of <count>

Examples:
|phrase|make|model|count|
|Mustang|Ford|Mustang|1|
|Lacrosse|Buick|Lacrosse|1|
|X5|BMW|X5|1|
|A4|Audi|A4|2|
