Scenario: Car search by make

Given is the default data set
And an opened search specification page
When I want to search for <phrase>
And I input car make <make>
And I execute the search
Then the web application shows a search result page showing cars <make> <model> in size of <count>

Examples:
|phrase|make|model|count|
|Ford|Ford|Mustang|1|
|Buick|Buick|Lacrosse|1|
|BMW|BMW|X5|1|
|Audi|Audi|A4|2|
